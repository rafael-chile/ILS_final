package emcl.fub.blue.solver;

import emcl.fub.blue.entity.TimeSlot;
import org.apache.commons.lang3.ArrayUtils;
import org.sat4j.ExitCode;
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.WeightedMaxSatDecorator;
import org.sat4j.maxsat.SolverFactory;
//import org.sat4j.minisat.SolverFactory;
import org.sat4j.maxsat.reader.WDimacsReader;
import org.sat4j.opt.MaxSatDecorator;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.*;
import org.sat4j.tools.ModelIterator;
import org.sat4j.tools.OptToSatAdapter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SolverUtil {
    // temp!!!
    private WeightedMaxSatDecorator maxSatSolver = new WeightedMaxSatDecorator(SolverFactory.newLight());

    private WDimacsReader reader = new WDimacsReader(maxSatSolver);
    private int MAXVAR;
    private int NBCLAUSES;

    public SolverUtil(int maxvar, int nbclauses) {
        MAXVAR = maxvar;
        NBCLAUSES = nbclauses;
        maxSatSolver.newVar(MAXVAR);
        maxSatSolver.setExpectedNumberOfClauses(NBCLAUSES);

        maxSatSolver.setTimeout(14400); // 4 hour timeout
        maxSatSolver.setTopWeight(BigInteger.valueOf(20000));
    }


    public int[] solveSAT() throws URISyntaxException, TimeoutException, ContradictionException, IOException, ParseFormatException {

        System.out.println("Checking Satisfiability");

        if (maxSatSolver.isSatisfiable()) {
            System.out.println("Satisfiable");
            int[] model = maxSatSolver.model(); // model with maximal satisfiability
            System.out.println(reader.decode(model));
            return model;
        } else {
            System.out.println("hard clauses cannot be satisfied");
        }

        return null;
    }

    public void addHard(ArrayList<ArrayList<Integer>> clauses) throws ContradictionException {
        for (ArrayList<Integer> clause : clauses) {
            Integer[] c = clause.subList(0, clause.size()).toArray(new Integer[clause.size()]);
            maxSatSolver.addHardClause(new VecInt(ArrayUtils.toPrimitive(c)));
        }
    }

    public void addSoft(ArrayList<ArrayList<Integer>> clauses) throws ContradictionException {
        for (ArrayList<Integer> clause : clauses) {
            Integer[] c = clause.subList(0, clause.size()).toArray(new Integer[clause.size()]);
            maxSatSolver.addSoftClause(1, new VecInt(ArrayUtils.toPrimitive(c))); //.addHardClause(new VecInt(ArrayUtils.toPrimitive(c)));
        }
    }

    public void addExactly(ArrayList<ArrayList<Integer>> l) throws ContradictionException {
        for(ArrayList<Integer> literals : l){
            Integer lastElement=literals.get(literals.size() - 1);
            literals.remove(literals.size()-1);
            addExactlyClause(literals, lastElement);
        }

    }

    private void addExactlyClause(ArrayList<Integer> literals, int k) throws ContradictionException {
        int[] vector = new int[literals.size()];
        int ind = 0;
        for (Integer num : literals) {
            vector[ind] = num;
            ind++;
        }
        maxSatSolver.addExactly(new VecInt(vector), k);
    }


    public void addAtLeast(ArrayList<ArrayList<Integer>> clauses) throws ContradictionException {
        for (ArrayList<Integer> clause : clauses) {
            Integer[] c = clause.subList(0, clause.size() - 1).toArray(new Integer[clause.size() - 1]);
            addAtLeastClause(ArrayUtils.toPrimitive(c),
                    clause.get(clause.size() - 1).intValue());
        }
    }

    private void addAtLeastClause(int[] clause, int k) throws ContradictionException {
        maxSatSolver.addSoftAtLeast(1, new VecInt(clause), k);

    }

    public void addAtLeastHard(ArrayList<ArrayList<Integer>> clauses) throws ContradictionException {
        for (ArrayList<Integer> clause : clauses) {
            Integer[] c = clause.subList(0, clause.size() - 1).toArray(new Integer[clause.size() - 1]);
            addAtLeastClauseHard(ArrayUtils.toPrimitive(c),
                    clause.get(clause.size() - 1).intValue());
        }
    }

    private void addAtLeastClauseHard(int[] clause, int k) throws ContradictionException {
        maxSatSolver.addAtLeast(new VecInt(clause), k);

    }

    public void addAtMost(ArrayList<ArrayList<Integer>> clauses) throws ContradictionException {
        for (ArrayList<Integer> clause : clauses) {
            Integer[] c = clause.subList(0, clause.size() - 1).toArray(new Integer[clause.size() - 1]);
            addAtMostClause(ArrayUtils.toPrimitive(c),
                    clause.get(clause.size() - 1).intValue());
        }
    }

    private void addAtMostClause(int[] clause, int k) throws ContradictionException {
        maxSatSolver.addSoftAtMost(1, new VecInt(clause), k);

    }


    ///  optimization
    public int[] optimize() throws TimeoutException {
        boolean isSatisfiable = false;
        IOptimizationProblem optproblem = new MaxSatDecorator(maxSatSolver);
        try {
            // int[] lastModel;
            while (optproblem.admitABetterSolution() && (optproblem.getObjectiveValue().intValue() > 560)) {
                if (!isSatisfiable) {
                    if (optproblem.nonOptimalMeansSatisfiable()) {
                        //setExitCode(ExitCode.SATISFIABLE);
                        System.out.println("setExitCode(ExitCode.SATISFIABLE);");
                        if (optproblem.hasNoObjectiveFunction()) {
                            return null;
                        }
                        System.out.println("log(SATISFIABLE)");
                        //log("SATISFIABLE"); //$NON-NLS-1$
                    }
                    isSatisfiable = true;
                    //log("OPTIMIZING..."); //$NON-NLS-1$
                    System.out.println("Optimizing...");
                }
                System.out.println("Got one, discarding .." + optproblem.getObjectiveValue());

                optproblem.discardCurrentSolution();
            }
            if (isSatisfiable) {
                System.out.println("setExitCode(ExitCode.OPTIMUM_FOUND);");
                return optproblem.model();
            } else {
                System.out.println("setExitCode(ExitCode.UNSATISFIABLE);");
                return null;
            }
        } catch (ContradictionException ex) {
            assert isSatisfiable;
            System.out.println("setExitCode(ExitCode.OPTIMUM_FOUND);");

        }
        return null;
    }

    public WeightedMaxSatDecorator getMaxSatSolver() {
        return maxSatSolver;
    }
}
