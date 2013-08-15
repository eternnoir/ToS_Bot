package puzzleslove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Stack;

import android.R.integer;
import android.annotation.SuppressLint;

public class puzzleSolver {

	private int _maxMove;
	private int _minMove;
	private int _eightDSupport;
	private int[][] _board;
	private int _ComboNumber;
	private boolean _maxFind;
	private int _perferOrb;
	private solution _bestS;
	private int _move;

	public puzzleSolver(int min, int maxMove, int eightDSupport, int cm) {
		_minMove = min;
		_maxMove = maxMove;
		_eightDSupport = eightDSupport;
		_ComboNumber = cm;
		_maxFind = false;
		_bestS = null;
	}

	public ArrayList<solution> solveBoard() {
		ArrayList<solution> ret = new ArrayList<solution>();

		return ret;
	}

	public solution solve_board(int[][] board) {
		_board = board;
		ArrayList<solution> solutions = new ArrayList<solution>();
		solution seed_solution = null;
		seed_solution = make_solution(board, 0, 0);
		inPlaceSolution(seed_solution);

		for (int i = 0, s = 0; i < 5; ++i) {
			for (int j = 0; j < 6; ++j, ++s) {

				if (i == 0 || i == 4 || j == 0 || j == 5) {
				solutions.add(copySolutionCursor(seed_solution, i, j));
			//	solutions.add(copySolutionCursor(seed_solution, 0, 0));
				// solutions.add(copySolutionCursor(seed_solution, 4, 0));
				// solutions.add(copySolutionCursor(seed_solution, 0, 5));
				// solutions.add(copySolutionCursor(seed_solution, 4, 5));
				}

			}
		}

		solveState solve_state = new solveState(_maxMove, _eightDSupport, 0,
				solutions);

		solve_board_step(solve_state);
		int bs=0;
		if(_bestS != null){
			return _bestS;
		}
		for(int i =0;i<solve_state.solutions.size();i++){
			if(solve_state.solutions.get(i).matches.size()>bs){
				bs = solve_state.solutions.get(i).matches.size();
				_bestS = solve_state.solutions.get(i);
			}
		}
		return _bestS;
	}

	public ArrayList<matchPair> findComboMatch(int[][] board, solution s) {
		int[][] matchBoard = new int[5][6];
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 6; k++)
				matchBoard[i][k] = -1;
		}

		// find horizontals for 3x
		for (int i = 0; i < 5; ++i) { // ROW
			int prev_1_orb = -5;
			int prev_2_orb = -5;
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
			int prev_1_orb = -5;
			int prev_2_orb = -5;
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
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 6; j++)
				scratchBoard[i][j] = matchBoard[i][j];
		s.setCB(matchBoard);
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

	public solution make_solution(int[][] board, int h, int w) {
		solution ret;
		ret = new solution(board, new pos(0, 0), new pos(0, 0),
				new ArrayList<Integer>(), false, new ArrayList<matchPair>());

		return ret;
	}

	public solution copySolutionCursor(solution s, int i, int j) {
		return new solution(s.board, new pos(i, j), new pos(i, j), s.path,
				s.is_done, new ArrayList<matchPair>());
	}

	public solution copySolutionCursorWithI(solution s, int i, int j, pos ini) {
		return new solution(s.board, new pos(i, j), new pos(i, j), s.path,
				s.is_done, new ArrayList<matchPair>());
	}

	public void inPlaceSolution(solution s) {

	}

	public void solve_board_step(solveState s) {
		if (_maxFind && s.p > _minMove) {
			return;
		}
		if (s.p >= _maxMove) {
			return;
		}
		++s.p;
		s.solutions = evolve_solutions(s.solutions, s.dir_step,0);
		// solve_board_step(s);
	}

	public ArrayList<solution> evolve_solutions(ArrayList<solution> solutions,
			int dir_step,int deep) {
		ArrayList<solution> new_solutions = new ArrayList<solution>();
		if(deep>= _maxMove){
			int i =0;
			i=i;
			return new_solutions;
		}
		if (_maxFind && deep > _minMove) {
			int i =0;
			i=i;
			return new_solutions;
		}
		//if(_move > 2000){
		//	return new_solutions;
		//}
		deep ++;
		
		for (solution s : solutions) {
			if (s.is_done) {
				continue;
			}
			if(s.path.size()>4 && s.matches.size()<2){
				s.is_done = true;
				continue;
			}
			if(s.path.size()>8 && s.matches.size()<3){
				s.is_done = true;
				continue;
			}
			for (int dir = 0; dir < 8; dir += dir_step) {
				if (!can_move_orb_in_solution(s, dir)) {
					continue;
				}
				solution _solution = new solution(s.board, new pos(s.cursor.h,
						s.cursor.w), new pos(s.initcursor.h, s.initcursor.w),
						s.path, s.is_done, s.matches);

				in_place_swap_orb_in_solution(_solution, dir);
				_move++;
				in_place_evaluate_solution(_solution);
				new_solutions.add(_solution);
			}
			s.is_done = true;
		}
		solutions.addAll(evolve_solutions(new_solutions, dir_step,deep));
		return solutions;
	}

	public boolean can_move_orb_in_solution(solution s, int dir) {
		if (s.path.size() == 0) {
			return can_move_orb(s.cursor, dir);

		} else if (s.path.get(s.path.size() - 1).intValue() == ((dir + 4) % 8)) {
			return false;
		}
		return can_move_orb(s.cursor, dir);
	}

	public boolean can_move_orb(pos rc, int d) {
		int COLS = 6;
		int ROWS = 5;
		Boolean flag = false;
		switch (d) {
		case 0:
			flag = rc.w < (COLS - 1);
			break;
		case 1:
			flag = (rc.h < (ROWS - 1)) && (rc.w < (COLS - 1));
			break;
		case 2:
			flag = rc.h < (ROWS - 1);
			break;
		case 3:
			flag = (rc.h < (ROWS - 1)) && (rc.w > 0);
			break;
		case 4:
			flag = rc.w > 0;
			break;
		case 5:
			flag = (rc.h > 0) && (rc.w > 0);
			break;
		case 6:
			flag = rc.h > 0;
			break;
		case 7:
			flag = (rc.h > 0) && (rc.w < (COLS - 1));
			break;
		}
		return flag;
	}

	public void in_place_swap_orb_in_solution(solution s, Integer dir) {
		in_place_swap_orb(s.board, s.cursor, dir);
		s.path.add(dir);
	}

	public void in_place_swap_orb(int[][] board, pos rc, Integer dir) {
		pos old_rc = new pos(rc.h, rc.w);
		in_place_move_rc(rc, dir);
		int orig_type = board[old_rc.h][old_rc.w];
		board[old_rc.h][old_rc.w] = board[rc.h][rc.w];
		board[rc.h][rc.w] = orig_type;
	}

	public void in_place_move_rc(pos rc, Integer dir) {
		switch (dir.intValue()) {
		case 0:
			rc.w += 1;
			break;
		case 1:
			rc.h += 1;
			rc.w += 1;
			break;
		case 2:
			rc.h += 1;
			break;
		case 3:
			rc.h += 1;
			rc.w -= 1;
			break;
		case 4:
			rc.w -= 1;
			break;
		case 5:
			rc.h -= 1;
			rc.w -= 1;
			break;
		case 6:
			rc.h -= 1;
			break;
		case 7:
			rc.h -= 1;
			rc.w += 1;
			break;
		}
	}

	@SuppressLint("NewApi")
	public void in_place_evaluate_solution(solution s) {
		int[][] current_board = new int[5][6];
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 6; j++)
				current_board[i][j] = s.board[i][j];

		ArrayList<matchPair> all_matches = new ArrayList<matchPair>();
		while (true) {
			ArrayList<matchPair> matches = findComboMatch(current_board, s);
			if (matches.size() == 0) {
				break;
			}
			in_place_remove_matches(current_board, s.currentboard);
			in_place_drop_empty_spaces(current_board);
			all_matches.addAll(matches);
			break;
		}
		if (all_matches.size() >= _ComboNumber) {
			_bestS = s;
			_maxFind = true;
		}
		s.matches = all_matches;
	}

	public void in_place_remove_matches(int[][] cb, int[][] mb) {
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 6; ++j) {
				if (mb[i][j] != -1) {
					cb[i][j] = -8;
				}
			}
		}
	}

	public void in_place_drop_empty_spaces(int[][] board) {
		for (int j = 0; j < 6; ++j) {
			int dest_i = 5 - 1;
			for (int src_i = 5 - 1; src_i >= 0; --src_i) {
				if (board[src_i][j] != -1) {
					board[dest_i][j] = board[src_i][j];
					--dest_i;
				}
			}
			for (; dest_i >= 0; --dest_i) {
				board[dest_i][j] = 'X';
			}
		}
	}

}
