package puzzleslove;

import java.util.ArrayList;
import java.util.Stack;

public class puzzleSolver {

	private int _maxMove;
	private int _eightDSupport;
	private int[][] _board;

	public puzzleSolver(int[][] board, int maxMove, int eightDSupport) {
		_maxMove = maxMove;
		_eightDSupport = eightDSupport;
		_board = board;
	}

	public ArrayList<solutions> solveBoard() {
		ArrayList<solutions> ret = new ArrayList<solutions>();

		return ret;
	}

	public ArrayList<matchPair> findComboMatch(int[][] board) {
		int[][] matchBoard = new int[5][6];
		java.util.Arrays.fill(matchBoard,-1);

		// find horizontals for 3x
		for (int i = 0; i < 5; ++i) { // ROW
			int prev_1_orb = -1;
			int prev_2_orb = -1;
			for (int j = 0; j < 6; ++j) { // COLS
				int cur_orb = board[i][j];
				if (prev_1_orb == prev_2_orb && prev_2_orb == cur_orb
						&& cur_orb != -1) {
					matchBoard[i][j] = cur_orb;
					matchBoard[i][j - 1] = cur_orb;
					matchBoard[i][j - 2] = cur_orb;
				}
				prev_1_orb = prev_2_orb;
				prev_2_orb = cur_orb;
			}
		}
		// find verticals for 3x
		for (int j = 0; j < 6; ++j) { // cols
			int prev_1_orb = -1;
			int prev_2_orb = -1;
			for (int i = 0; i < 5; ++i) { // rows
				int cur_orb = board[i][j];
				if (prev_1_orb == prev_2_orb && prev_2_orb == cur_orb
						&& cur_orb != -1) {
					matchBoard[i][j] = cur_orb;
					matchBoard[i - 1][j] = cur_orb;
					matchBoard[i - 2][j] = cur_orb;
				}
				prev_1_orb = prev_2_orb;
				prev_2_orb = cur_orb;
			}
		}
		int[][] scratchBoard = new int[5][6];
		System.arraycopy(matchBoard, 0, scratchBoard, 0, matchBoard.length);
		ArrayList<matchPair> ret = new ArrayList<matchPair>();
		int ROWS = 5;
		int COLS = 6;
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 6; ++j) {
				int cur_orb = scratchBoard[i][j];
				if (cur_orb == -1) {
					continue;
				}
				Stack<pos> stack = new Stack<pos>();
				stack.add(new pos(i, j));
				int count = 0;
				while (stack.size() > 0) {
					pos n = stack.pop();
					if (scratchBoard[n.h][n.w] != cur_orb) {
						continue;
					}
					++count;
					scratchBoard[n.h][n.w] = -1;
					if (n.h > 0) {
						stack.add(new pos(n.h - 1, n.w));
					}
					if (n.h < ROWS - 1) {
						stack.add(new pos(n.h + 1, n.w));
					}
					if (n.w > 0) {
						stack.add(new pos(n.h, n.w - 1));
					}
					if (n.w < COLS - 1) {
						stack.add(new pos(n.h, n.w + 1));
					}
				}
				ret.add(new matchPair(cur_orb, count));
			}
		}
		return ret;
	}
}
