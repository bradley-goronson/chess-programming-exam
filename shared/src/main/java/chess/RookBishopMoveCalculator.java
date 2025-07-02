package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookBishopMoveCalculator implements PieceMoveCalculator {
    int[][] positionAdjustments;

    RookBishopMoveCalculator(int[][] positionAdjustments) {
        this.positionAdjustments = positionAdjustments;
    }

    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessPiece currentPiece = board.getPiece(position);
        ChessGame.TeamColor currentColor = currentPiece.getTeamColor();

        for (int[] adjustment : positionAdjustments) {
            int currentRow = position.getRow();
            int currentColumn = position.getColumn();
            int nextRow = currentRow + adjustment[0];
            int nextColumn = currentColumn + adjustment[1];
            boolean foundOtherPiece = false;

            while (!foundOtherPiece && nextRow >= 1 && nextRow <= 8 && nextColumn >= 1 && nextColumn <= 8) {
                ChessPosition candidatePosition = new ChessPosition(nextRow, nextColumn);
                ChessPiece candidatePiece = board.getPiece(candidatePosition);
                if (candidatePiece != null) {
                    if (candidatePiece.getTeamColor() != currentColor) {
                        validMoves.add(new ChessMove(position, candidatePosition, null));
                    }
                    foundOtherPiece = true;
                } else {
                    validMoves.add(new ChessMove(position, candidatePosition, null));
                }
                currentRow += adjustment[0];
                currentColumn += adjustment[1];
                nextRow = currentRow + adjustment[0];
                nextColumn = currentColumn + adjustment[1];
            }
        }
        return validMoves;
    }
}
