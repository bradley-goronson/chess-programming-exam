package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingKnightMoveCalculator implements PieceMoveCalculator {
    int[][] positionAdjustments;

    KingKnightMoveCalculator(int[][] positionAdjustments) {
        this.positionAdjustments = positionAdjustments;
    }

    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessPiece currentPiece = board.getPiece(position);
        ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        for (int[] adjustment : positionAdjustments) {
            int candidateRow = currentRow + adjustment[0];
            int candidateColumn = currentColumn + adjustment[1];
            if (candidateRow >= 1 && candidateRow <= 8 && candidateColumn >= 1 && candidateColumn <= 8) {
                ChessPosition candidatePosition = new ChessPosition(candidateRow, candidateColumn);
                ChessPiece candidatePiece = board.getPiece(candidatePosition);
                if (candidatePiece != null) {
                    if (candidatePiece.getTeamColor() != currentColor) {
                        validMoves.add(new ChessMove(position, candidatePosition, null));
                    }
                } else {
                    validMoves.add(new ChessMove(position, candidatePosition, null));
                }
            }
        }
        return validMoves;
    }
}
