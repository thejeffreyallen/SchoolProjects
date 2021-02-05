/**
 * DFA implementation
 * 
 * @Author Jeff Allen
 * @see DFAInterface
 * 
 * class - CS361
 * Project 1 
 */

package fa.dfa;

import java.util.LinkedHashSet;
import java.util.Set;
import fa.State;

public class DFA implements DFAInterface {

	private LinkedHashSet<DFAState> states; // linked hash set to store and maintain original order of states
	private LinkedHashSet<Character> alphabet; // linked hash set to store and maintain original order of alphabet
	private DFAState startState; // The start state

	/**
	 * Default constructor - Instantiates the two linked hash sets needed to keep
	 * track of the DFA
	 */
	public DFA() {
		this.states = new LinkedHashSet<DFAState>();
		this.alphabet = new LinkedHashSet<Character>();
	}

	@Override
	public void addStartState(String name) {
		startState = new DFAState(name, true, false); // Set the global startState variable
		states.add(startState); // Add the start state
	}

	@Override
	public void addState(String name) {
		states.add(new DFAState(name)); // Add a normal state (not final or start)
	}

	@Override
	public void addFinalState(String name) {
		states.add(new DFAState(name, false, true)); // Add a new final state
	}

	@Override
	public void addTransition(String fromState, char onSymb, String toState) {
		alphabet.add(onSymb); // Build the alphabet from given transitions
		DFAState from = null; // temporary variable to hold state
		DFAState to = null; // temporary variable to hold state

		for (DFAState s : states) { // iterate through all states
			if (s.getName().equals(fromState)) // look for a matching state name
				from = s;
			if (s.getName().equals(toState)) // look for a matching state name
				to = s;
		}
		from.addTransition(onSymb, to); // Add a transition from the 'from' state to the 'to' state on the onSymb
										// character
	}

	@Override
	public Set<? extends State> getStates() {
		return states;
	}

	@Override
	public Set<? extends State> getFinalStates() {
		Set<DFAState> temp = new LinkedHashSet<DFAState>();
		for (DFAState s : states) {
			if (s.isFinal())
				temp.add(s);
		}
		return temp;
	}

	@Override
	public State getStartState() {
		return startState;
	}

	@Override
	public Set<Character> getABC() {
		return alphabet;
	}

	@Override
	public boolean accepts(String s) {
		if (alphabet.size() == 0) {
			return false;
		}
		DFAState current = startState;
		for (char c : s.toCharArray()) {
			current = current.getToState(c);
		}
		if (current == null)
			return false;
		return current.isFinal();
	}

	@Override
	public State getToState(DFAState from, char onSymb) {
		return from.getToState(onSymb);
	}

//	/**
//	 * Construct the textual representation of the DFA, for example A simple two
//	 * state DFA Q = { a b } Sigma = { 0 1 } delta = 0 1 a a b b a b q0 = a F = { b
//	 * }
//	 * 
//	 * The order of the states and the alphabet is the order in which they were
//	 * instantiated in the DFA.
//	 * 
//	 * @return String representation of the DFA
//	 */
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		String fin = ""; // To hold the final state(s) for easier access later.
//		String alpha = ""; // to hold the alphabet characters for easier access later.
//		String delta = ""; // to hold the alphabet characters formatted for the delta section for easier
//							// access later.
//		sb.append("Q = { ");
//		for (DFAState f : states) { // Iterate through each state
//			sb.append(" " + f.getName() + " "); // build the string with the state names
//			if (f.isFinal()) // Find the final state(s)
//				fin += " " + f.getName() + " ";
//		}
//		sb.append(" }");
//
//		for (char c : alphabet) {
//			alpha += " " + c + " "; // build the alphabet
//			delta += "    " + c + "    ";
//		}
//
//		sb.append("\nSigma = { ").append(alpha).append(" }");
//		sb.append("\nDelta = \n").append("            "); // write the whitespace to format the string properly
//		sb.append(delta);
//		sb.append("\n       "); // write the whitespace to format the string properly
//
//		for (DFAState f : states) { // loop through each state
//			sb.append(f.getName()).append("        "); // write the state name i.e. state A
//			for (char c : alphabet) // loop through each alphabet character
//				sb.append(f.getToState(c).getName()).append("        "); // write the transition 'to' state for the
//																			// given alphabet character i.e. state A ->
//																			// on a 1 -> state B. This would write a B.
//			sb.append("\n       "); // write the whitespace to format the string properly
//		}
//		sb.append("\nq0 = ").append(startState); // Write the start state
//		sb.append("\nF = { ").append(fin).append(" }\n"); // Write the final state(s)
//		return sb.toString(); // Return the whole formatted string
//	}
	
	/**
	 * Construct the textual representation of the DFA, for example A simple two
	 * state DFA Q = { a b } Sigma = { 0 1 } delta = 0 1 a a b b a b q0 = a F = { b
	 * }
	 * 
	 * The order of the states and the alphabet is the order in which they were
	 * instantiated in the DFA.
	 * 
	 * @return String representation of the DFA
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String fin = ""; // To hold the final state(s) for easier access later.
		String alpha = ""; // to hold the alphabet characters for easier access later.
		String delta = ""; // to hold the alphabet characters formatted for the delta section for easier
							// access later.
		sb.append("Q = { ");
		for (DFAState f : states) { // Iterate through each state
			sb.append(" " + f.getName() + " "); // build the string with the state names
			if (f.isFinal()) // Find the final state(s)
				fin += " " + f.getName() + " ";
		}
		sb.append(" }");

		for (char c : alphabet) {
			alpha += " " + c + " "; // build the alphabet
			delta += c + "    |    ";
		}

		sb.append("\nSigma = { ").append(alpha).append(" }");
		sb.append("\nDelta = \n").append("            "); // write the whitespace to format the string properly
		sb.append("\n                                 \n"); // write the whitespace to format the string properly
		sb.append("       ").append("     |    ").append(delta);
		for (DFAState f : states) { // loop through each state
			sb.append("\n    -----------------------------\n"); // write the whitespace and dashes to format the string properly
			sb.append("       "); // write the whitespace to format the string properly
			sb.append(f.getName()).append("    |    "); // write the state name i.e. state A
			for (char c : alphabet) // loop through each alphabet character
				sb.append(f.getToState(c).getName()).append("    |    "); // write the transition 'to' state for the
																			// given alphabet character i.e. state A ->
																			// on a 1 -> state B. This would write a B.
		}
		sb.append("\n    -----------------------------\n"); // write the whitespace and dashes to format the string properly
		sb.append("\nq0 = ").append(startState); // Write the start state
		sb.append("\nF = { ").append(fin).append(" }\n"); // Write the final state(s)
		return sb.toString(); // Return the whole formatted string
	}

}
