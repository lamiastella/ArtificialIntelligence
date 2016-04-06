/** Mona Jalal **/
import java.util.*;

public class PlayerImpl implements Player {
	// Identifies the player
	private int name = 0;
	int n = 0;

	// Constructor
	public PlayerImpl(int name, int n) {
		this.name = 0;
		this.n = n;
	}

	// Function to find possible successors
	@Override
	public ArrayList<Integer> generateSuccessors(int lastMove, int[] crossedList) {
		ArrayList<Integer> successors = new ArrayList<Integer>();
		//if it's the first move
		if (lastMove == -1) {
			for (int i = 2; i < ((double) n / 2.0); i += 2) 
				successors.add(i);
		//if it's not the first move
		} else {
			for (int i = 1; i <= n; i++) {
				if ((i % lastMove == 0 || lastMove % i == 0)
						&& (crossedList[i] == 0)) {
					successors.add(i);
				}
			}
		}
		return successors;
	}

	// The max value function
	@Override
	public int max_value(GameState s) {
		// TODO Add your code here
		int alpha=Integer.MIN_VALUE; //alpha=-inf
		ArrayList<Integer> successors = generateSuccessors(s.lastMove, s.crossedList);
		int size=successors.size();
		//if there's no successor set the leaf to true
		if (size==0){
			s.leaf=true;
			return -1;
		}
		else {
			for (int i=0; i<size ;i++){
				GameState child = new GameState(s.crossedList, successors.get(i));
				child.crossedList[successors.get(i)]=1; 
				int minimum = min_value(child);
				if(alpha <= minimum) {
					alpha=minimum;
					s.bestMove = successors.get(i);
				}
			}
		}
		return alpha;
	}

	// The min value function
	@Override
	public int min_value(GameState s) {
		// TODO Add your code here
		int beta=Integer.MAX_VALUE; //alpha=-inf
		ArrayList<Integer> successors = generateSuccessors(s.lastMove, s.crossedList);
		int size=successors.size();
		//if there's no successor set it as leaf node
		if (size==0){
			s.leaf=true;
			return 1;
		}
		else {
			for (int i=0; i<size ;i++){
				GameState child = new GameState(s.crossedList, successors.get(i));
				child.crossedList[successors.get(i)]=1; 
				int maximum = max_value(child);
				if(beta >= maximum) {
					beta=maximum;
					s.bestMove = successors.get(i);
				}
			}
		}
		return beta;
	}

	// Function to find the next best move
	@Override
	public int move(int lastMove, int[] crossedList) {
		// TODO Add your code here
		GameState state=new GameState(crossedList,lastMove);
		int maxValue= max_value(state);
		if(state.leaf==true)
			return -1;
		return state.bestMove;
	}
}