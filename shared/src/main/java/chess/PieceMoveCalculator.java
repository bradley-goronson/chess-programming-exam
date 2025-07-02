package chess;

import java.util.Collection;

public interface PieceMoveCalculator {
    Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition position);
}
