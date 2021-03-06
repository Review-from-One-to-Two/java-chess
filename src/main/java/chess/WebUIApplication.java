package chess;

import chess.controller.ChessGame;
import chess.dao.DataSource;
import chess.dao.JdbcPieceDao;
import chess.dao.PieceDao;
import chess.dto.PieceDto;
import spark.ModelAndView;
import spark.Route;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class WebUIApplication {
    private static HandlebarsTemplateEngine handlebarsTemplateEngine = new HandlebarsTemplateEngine();

    public static void main(String[] args) {
        port(8080);
        staticFileLocation("/templates");

        PieceDao pieceDao = new JdbcPieceDao(new DataSource());
        ChessGame chessGame = new ChessGame(pieceDao);

        Route route = (req, res) -> {
            Map<String, Object> model = new LinkedHashMap<>();
            List<PieceDto> pieces;
            try {
                pieces = chessGame.run(req.queryParams("command"));
            } catch (RuntimeException e) {
                pieces = chessGame.stay();
                model.put("error", e.getMessage());
            }
            model.put("pieces", pieces);
            return render(model, "index.hbs");
        };

        get("/", route);

        post("/", route);
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return handlebarsTemplateEngine.render(new ModelAndView(model, templatePath));
    }
}
