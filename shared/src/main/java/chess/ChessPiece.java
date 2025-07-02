package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor pieceColor;
    PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        if (this.type == PieceType.KING) {
            int[][] kingAdjustments = {{1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}, {-1, -1}, {-1, 1}};
            PieceMoveCalculator kingMoveCalculator = new KingKnightMoveCalculator(kingAdjustments);
            validMoves = kingMoveCalculator.getPieceMoves(board, myPosition);
        }
        if (this.type == PieceType.KNIGHT) {
            int[][] knightAdjustments = {{2, -1}, {2, 1}, {-2, 1}, {-2, -1}, {1, -2}, {-1, -2}, {1, 2}, {-1, 2}};
            PieceMoveCalculator knightMoveCalculator = new KingKnightMoveCalculator(knightAdjustments);
            validMoves = knightMoveCalculator.getPieceMoves(board, myPosition);
        }
        if (this.type == PieceType.ROOK) {
            int[][] rookAdjustments = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
            PieceMoveCalculator rookMoveCalculator = new RookBishopMoveCalculator(rookAdjustments);
            validMoves = rookMoveCalculator.getPieceMoves(board, myPosition);
        }
        if (this.type == PieceType.BISHOP) {
            int[][] bishopAdjustments = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            PieceMoveCalculator bishopMoveCalculator = new RookBishopMoveCalculator(bishopAdjustments);
            validMoves = bishopMoveCalculator.getPieceMoves(board, myPosition);
        }
        if (this.type == PieceType.QUEEN) {
            int[][] bishopAdjustments = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            PieceMoveCalculator bishopMoveCalculator = new RookBishopMoveCalculator(bishopAdjustments);
            int[][] rookAdjustments = {{1, 0}, {0, -1}, {0, 1}, {-1, 0}};
            PieceMoveCalculator rookMoveCalculator = new RookBishopMoveCalculator(rookAdjustments);

            validMoves.addAll(bishopMoveCalculator.getPieceMoves(board, myPosition));
            validMoves.addAll(rookMoveCalculator.getPieceMoves(board, myPosition));
        }
        if (this.type == PieceType.PAWN) {
            PieceMoveCalculator pawnMoveCalculator = new PawnMoveCalculator();
            validMoves = pawnMoveCalculator.getPieceMoves(board, myPosition);
        }

        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
