package chess.domain.piece;

import java.util.Map;

import chess.domain.Team;
import chess.domain.piece.strategy.MovingStrategy;
import chess.domain.position.Position;

public class Pawn extends Piece {

	public static final String WHITE_PAWN = "\u2659";
	public static final String BLACK_PAWN = "\u265f";

	public Pawn(Team team, Position position) {
		super(team, position);
	}

	public static Piece of(Team team, Position position) {
		return new Pawn(team, position);
	}

	@Override
	public String toString() {
		if (team.equals(Team.WHITE)) {
			return WHITE_PAWN;
		}
		return BLACK_PAWN;
	}

	@Override
	public Piece move(Position from, Position to, Map<Position, Team> dto) {
		return null;
	}
}