package edu.bsu.cs222.nba.player.stats.view;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIController extends GridPane {

    public interface StatViewProductionListener {
        void onStatViewsProduced(StatView statView);
    }

    private GridPane ui;
    private ControlPanel controlPanel;
    private StatView seasonStatView;
    private StatView careerStatView;
    private GridPane playerResultArea = new GridPane();
    private HeadshotLogoView headshotLogoView;
    private PlayerInfoView playerInfoView;
    private StackPane statsPane;
    private Button seasonOrCareerButton;
    private Label seasonOrCareerLabel;
    private final boolean isSecondPlayer;
    private final List<StatViewProductionListener> listeners = new ArrayList<>();

    public UIController(boolean isSecondPlayer){
        this.isSecondPlayer = isSecondPlayer;
        GridPane pane = createUI();
        getChildren().add(pane);
    }


    private GridPane createUI() {
        controlPanel = new ControlPanel();
        ui = new GridPane();
        listenForPlayerStats();
        setupContainers();
        return ui;
    }

    private void setupContainers() {
        ui.getChildren().addAll(controlPanel, playerResultArea);
        GridPane.setConstraints(controlPanel, 0, 0);
        GridPane.setConstraints(playerResultArea, 0, 1);
    }

    private void listenForPlayerStats() {
        controlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
            playerResultArea = makeResultArea(resultGenerationEvent);
            fireEvent();
        }));
    }

    private GridPane makeResultArea(ResultGenerationEvent resultGenerationEvent) {
        playerResultArea.getChildren().clear();
        seasonOrCareerLabel = new Label(controlPanel.getSeason());
        seasonOrCareerLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
        seasonOrCareerButton = makeSeasonOrCareerButton();
        statsPane = makeStatViewPane(resultGenerationEvent);
        playerInfoView = makePlayerInfoView(resultGenerationEvent);
        headshotLogoView = makeHeadShotLogoView();
        playerResultArea.getChildren().addAll(headshotLogoView,
                playerInfoView, seasonOrCareerLabel, seasonOrCareerButton, statsPane);
        formatResultArea(isSecondPlayer);
        return playerResultArea;
    }

    private void formatResultArea(boolean isSecondPlayer){
        if (isSecondPlayer){
            formatRightPlayerResultArea();
        } else {
            formatLeftPlayerResultArea();
        }
    }

    private void formatLeftPlayerResultArea() {
        GridPane.setConstraints(headshotLogoView, 0, 0);
        GridPane.setConstraints(playerInfoView, 1, 0);
        GridPane.setConstraints(seasonOrCareerLabel, 1, 1);
        GridPane.setConstraints(seasonOrCareerButton, 0, 2);
        GridPane.setConstraints(statsPane, 1, 2);
        GridPane.setHalignment(seasonOrCareerButton, HPos.CENTER);
        GridPane.setHalignment(seasonOrCareerLabel, HPos.RIGHT);
    }

    private void formatRightPlayerResultArea() {
        GridPane.setConstraints(headshotLogoView, 1, 0);
        GridPane.setConstraints(playerInfoView, 0, 0);
        GridPane.setConstraints(seasonOrCareerLabel, 0, 1);
        GridPane.setConstraints(seasonOrCareerButton, 1, 2);
        GridPane.setConstraints(statsPane, 0, 2);
        GridPane.setHalignment(seasonOrCareerButton, HPos.CENTER);
        GridPane.setHalignment(seasonOrCareerLabel, HPos.LEFT);
    }

    private StackPane makeStatViewPane(ResultGenerationEvent resultGenerationEvent){
        seasonStatView = new StatView(resultGenerationEvent.seasonPlayerStats, isSecondPlayer);
        careerStatView = new StatView(resultGenerationEvent.careerPlayerStats, isSecondPlayer);
        alignStatView();
        careerStatView.setVisible(false);
        return new StackPane(seasonStatView, careerStatView);
    }

    private void alignStatView() {
        if (isSecondPlayer) {
            seasonStatView.setAlignment(Pos.CENTER_LEFT);
            careerStatView.setAlignment(Pos.CENTER_LEFT);
        } else {
            seasonStatView.setAlignment(Pos.CENTER_RIGHT);
            careerStatView.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    private PlayerInfoView makePlayerInfoView(ResultGenerationEvent resultGenerationEvent){
        playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
        alignPlayerInfo();
        return playerInfoView;
    }

    private void alignPlayerInfo() {
        if (isSecondPlayer) {
            playerInfoView.setAlignment(Pos.CENTER_LEFT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
        } else {
            playerInfoView.setAlignment(Pos.CENTER_RIGHT);
            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_RIGHT);
        }
    }

    private Button makeSeasonOrCareerButton() {
        Button button = new Button("See Career Stats!");
        button.setAlignment(Pos.CENTER);
        button.setOnAction(event -> {
            highlightLabelBlack(seasonStatView.getListOfLabels());
            highlightLabelBlack(careerStatView.getListOfLabels());
            showProperStatView(button);
        });
        return button;
    }

    private void showProperStatView(Button button) {
        if (!careerStatView.isVisible()){
            careerStatView.setVisible(true);
            seasonStatView.setVisible(false);
            seasonOrCareerLabel.setText("Career Stats");
            button.setText("See Season Stats!");
        } else {
            careerStatView.setVisible(false);
            seasonStatView.setVisible(true);
            seasonOrCareerLabel.setText(controlPanel.getSeason());
            button.setText("See Career Stats!");
        }
    }

    private HeadshotLogoView makeHeadShotLogoView() {
        HeadshotLogoView headshotLogoView = HeadshotLogoView.withTeam(controlPanel.getTeam())
                .andPlayerName(controlPanel.getPlayer());
        generateHeadshotLogo(headshotLogoView);
        if (isSecondPlayer) {
            headshotLogoView.formatSecondPane();
        } else {
            headshotLogoView.formatFirstPane();
        }
        return headshotLogoView;
    }

    private void generateHeadshotLogo(HeadshotLogoView headshotLogoView) {
        try {
            headshotLogoView.generateHeadshot();
            headshotLogoView.generateLogo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StatView grabVisibleStatView() {
        if (seasonStatView.isVisible()){
            return seasonStatView;
        } else {
            return careerStatView;
        }
    }

    private void highlightLabelBlack(List<Label> list){
        for (Label label : list){
            label.setTextFill(Color.BLACK);
        }
    }

    public void addListeners(StatViewProductionListener listener){
        listeners.add(listener);
    }

    public void fireEvent(){
        StatView statView = grabVisibleStatView();
        for (StatViewProductionListener listener : listeners) {
            listener.onStatViewsProduced(statView);
        }
    }
}
