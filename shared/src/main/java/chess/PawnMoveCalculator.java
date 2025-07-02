package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMoveCalculator {
    public Collection<ChessMove> getPieceMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessPiece currentPiece = board.getPiece(position);
        ChessGame.TeamColor currentColor = currentPiece.getTeamColor();
        int currentRow = position.getRow();
        int currentColumn = position.getColumn();

        ChessPiece.PieceType[] promotionTypes = {
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK
        };

        int[][] positionAdjustments = {{1, 0}, {2, 0}, {1, -1}, {1, 1}};
        ArrayList<ChessPosition> candidatePositions = new ArrayList<>();

        for (int[] adjustment : positionAdjustments) {
            if (currentColor == ChessGame.TeamColor.WHITE) {
                candidatePositions.add(new ChessPosition(currentRow + adjustment[0], currentColumn + adjustment[1]));
            } else {
                candidatePositions.add(new ChessPosition(currentRow - adjustment[0], currentColumn + adjustment[1]));
            }
        }

        int loop = 1;
        boolean advanceOneValid = false;
        for (ChessPosition candidatePosition : candidatePositions) {
            int candidateRow = candidatePosition.getRow();
            int candidateColumn = candidatePosition.getColumn();
            boolean outOfBounds = true;

            if (candidateRow >= 1 && candidateRow <= 8 && candidateColumn >= 1 && candidateColumn <= 8) {
                outOfBounds = false;
            }

            if (loop == 1 && !outOfBounds && board.getPiece(candidatePosition) == null) {
                advanceOneValid = true;
                if (candidateRow == 1 || candidateRow == 8) {
                    for (ChessPiece.PieceType promotionType : promotionTypes) {
                        validMoves.add(new ChessMove(position, candidatePosition, promotionType));
                    }
                } else {
                    validMoves.add(new ChessMove(position, candidatePosition, null));
                }
            }

            if (loop == 2 && !outOfBounds && advanceOneValid && board.getPiece(candidatePosition) == null) {
                if ((currentRow == 2 && currentColor == ChessGame.TeamColor.WHITE) || (currentRow == 7 && currentColor == ChessGame.TeamColor.BLACK)) {
                    validMoves.add(new ChessMove(position, candidatePosition, null));
                }
            }

            if (loop > 2 && !outOfBounds && board.getPiece(candidatePosition) != null && board.getPiece(candidatePosition).getTeamColor() != currentColor) {
                if (candidateRow == 1 || candidateRow == 8) {
                    for (ChessPiece.PieceType promotionType : promotionTypes) {
                        validMoves.add(new ChessMove(position, candidatePosition, promotionType));
                    }
                } else {
                    validMoves.add(new ChessMove(position, candidatePosition, null));
                }
            }
            loop++;
        }
        return validMoves;
    }
}
