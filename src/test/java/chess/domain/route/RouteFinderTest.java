package chess.domain.route;

import chess.domain.Team;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
class RouteFinderTest {

    @ParameterizedTest
    @MethodSource("createPositionAndPieceType")
    void findRoute(Position fromPosition, Position toPosition, Team team, PieceType pieceType, Route expected) {
        Route foundRoute = RouteFinder.findRoute(fromPosition, toPosition, team, pieceType);

        assertThat(foundRoute).isEqualTo(expected);
    }

    private static Stream<Arguments> createPositionAndPieceType() {
        return Stream.of(
                Arguments.of(Position.of("A2"), Position.of("A3"), Team.WHITE, PieceType.PAWN,
                        new RouteToProceed(new ArrayList<>())),
                Arguments.of(Position.of("A2"), Position.of("A4"), Team.WHITE, PieceType.PAWN,
                        new RouteToProceed(Arrays.asList(Position.of("A3")))),
                Arguments.of(Position.of("A2"), Position.of("B3"), Team.WHITE, PieceType.PAWN,
                        new RouteToAttack(new ArrayList<>())),

                Arguments.of(Position.of("A7"), Position.of("A6"), Team.BLACK, PieceType.PAWN,
                        new RouteToProceed(new ArrayList<>())),
                Arguments.of(Position.of("A7"), Position.of("A5"), Team.BLACK, PieceType.PAWN,
                        new RouteToProceed(Arrays.asList(Position.of("A6")))),
                Arguments.of(Position.of("A7"), Position.of("B6"), Team.BLACK, PieceType.PAWN,
                        new RouteToAttack(new ArrayList<>())),

                Arguments.of(Position.of("B1"), Position.of("A3"), Team.WHITE, PieceType.KNIGHT,
                        new Route(new ArrayList<>())),
                Arguments.of(Position.of("B1"), Position.of("C3"), Team.WHITE, PieceType.KNIGHT,
                        new Route(new ArrayList<>())),

                Arguments.of(Position.of("B8"), Position.of("A6"), Team.BLACK, PieceType.KNIGHT,
                        new Route(new ArrayList<>())),
                Arguments.of(Position.of("B8"), Position.of("C6"), Team.BLACK, PieceType.KNIGHT,
                        new Route(new ArrayList<>()))
                );
    }
}