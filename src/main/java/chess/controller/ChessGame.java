package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandReader;
import chess.dao.PieceDao;
import chess.domain.board.Board;
import chess.domain.gamestate.GameState;
import chess.domain.gamestate.NothingHappened;
import chess.dto.PieceDto;
import utils.Assembler;

import java.sql.SQLException;
import java.util.List;

public class ChessGame {
    private PieceDao pieceDao;
    private GameState gameState;

    public ChessGame(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
        this.gameState = new NothingHappened();
    }

    public List<PieceDto> run(String input) {
        Command command = CommandReader.from(input);
        gameState = command.execute(gameState);
        Board board = gameState.getBoard();

        List<PieceDto> pieces = Assembler.convertMapToDTO(board.getPositionToPiece());
        savePieces(pieces);

        return pieces;
    }

    private void savePieces(List<PieceDto> pieces) {
        try {
            for (PieceDto piece : pieces) {
                pieceDao.updatePiece(piece);
            }
        } catch (SQLException e) {
            System.err.println("SQL 예외 발생 : " + e.getMessage());
        }
    }

    public List<PieceDto> stay() {
        Board board = gameState.getBoard();
        return Assembler.convertMapToDTO(board.getPositionToPiece());
    }
}