//package edu.bsu.cs222.nba.player.stats.view;
//
//import edu.bsu.cs222.nba.player.stats.model.ComparePlayerStat;
//import edu.bsu.cs222.nba.player.stats.model.PlayerStats;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//
//import java.io.IOException;
//
//public class UIController {
//
//    private HeadshotLogoView firstHeadshotLogoView;
//    private HeadshotLogoView secondHeadshotLogoView;
//    private ControlPanel firstControlPanel;
//    private ControlPanel secondControlPanel;
//    private VBox firstPlayerResultArea;
//    private VBox secondPlayerResultArea;
//    private Button differenceButton;
//
//    public UIController()
//
//    private VBox createUI() {
//        VBox firstContainer = buildFirstContainer();
//        VBox secondContainer = buildSecondContainer();
//        HBox ui = new HBox(50, firstContainer, secondContainer);
//        ui.setBackground(new Background(new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY)));
//        firstContainer.setPadding(new Insets(20, 0, 0, 10));
//        secondContainer.setPadding(new Insets(20, 0, 0, 0));
//        secondContainer.setAlignment(Pos.TOP_RIGHT);
//        listenForFirstPlayerStats();
//        listenForSecondPlayerStats();
//        listenForBothPlayers();
//        VBox mainUI = new VBox(ui, differenceButton);
//        return mainUI;
//    }
//
//    private VBox buildFirstContainer(){
//        firstControlPanel = new ControlPanel();
//        firstPlayerResultArea = new VBox();
//        return new VBox(firstControlPanel, firstPlayerResultArea);
//    }
//
//    private VBox buildSecondContainer(){
//        secondControlPanel = new ControlPanel();
//        secondPlayerResultArea = new VBox();
//        return new VBox(secondControlPanel, secondPlayerResultArea);
//    }
//
//    private void listenForFirstPlayerStats() {
//        firstControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
//            firstPlayerResultArea.getChildren().clear();
//            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
//            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
//            Label label = new Label(firstControlPanel.getSeason());
//            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
//            StackPane statsPane = new StackPane(seasonStats, careerStats);
//            Button seasonOrCareerButton = makeSeasonOrCareerButton(seasonStats, careerStats, firstControlPanel, label);
//            VBox vbox = new VBox(label, statsPane);
//            HBox buttonStatsBox = new HBox(125, seasonOrCareerButton, vbox);
//            careerStats.setVisible(false);
//            PlayerInfoView playerInfoView = makePlayerInfoView(resultGenerationEvent);
//            getFirstHeadshotLogoView();
//            HBox hbox = new HBox(firstHeadshotLogoView, playerInfoView);
//            firstPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
//            seasonStats.setAlignment(Pos.CENTER_RIGHT);
//            careerStats.setAlignment(Pos.CENTER_RIGHT);
//            buttonStatsBox.setAlignment(Pos.CENTER_RIGHT);
//            hbox.setAlignment(Pos.CENTER_RIGHT);
//        }));
//    }
//
//    private PlayerInfoView makePlayerInfoView(ResultGenerationEvent resultGenerationEvent){
//        PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
//        playerInfoView.setAlignment(Pos.CENTER_RIGHT);
//        playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_RIGHT);
//        return playerInfoView;
//    }
//
//    private void listenForSecondPlayerStats() {
//        secondControlPanel.addListeners(resultGenerationEvent -> Platform.runLater(() -> {
//            secondPlayerResultArea.getChildren().clear();
//            StatView seasonStats = new StatView(resultGenerationEvent.seasonPlayerStats);
//            StatView careerStats = new StatView(resultGenerationEvent.careerPlayerStats);
//            Label label = new Label(secondControlPanel.getSeason());
//            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
//            StackPane statsPane = new StackPane(seasonStats, careerStats);
//            Button button = makeSeasonOrCareerButton(seasonStats, careerStats, secondControlPanel, label);
//
//            VBox vbox = new VBox(label, statsPane);
//            HBox buttonStatsBox = new HBox(125, vbox, button);
//            careerStats.setVisible(false);
//            PlayerInfoView playerInfoView = PlayerInfoView.create(resultGenerationEvent.playerInfo);
//            getSecondHeadshotLogoView();
//            HBox hbox = new HBox(playerInfoView, secondHeadshotLogoView);
//            secondPlayerResultArea.getChildren().addAll(hbox, buttonStatsBox);
//            buttonStatsBox.setAlignment(Pos.CENTER_LEFT);
//            playerInfoView.setAlignment(Pos.CENTER_LEFT);
//            playerInfoView.getNameJerseyPositionBox().setAlignment(Pos.CENTER_LEFT);
//        }));
//    }
//
//    private void listenForBothPlayers(){
//
//        compareButton = makeSeeDifferenceButton(seasonStats, careerStats);
//    }
//
//    private Button makeSeasonOrCareerButton(StatView seasonStats, StatView careerStats, ControlPanel controlPanel, Label label) {
//        Button button = new Button("See Career Stats!");
//        button.setOnAction(event -> {
//            if (!careerStats.isVisible()){
//                careerStats.setVisible(true);
//                seasonStats.setVisible(false);
//                label.setText("Career Stats");
//                button.setText("See Season Stats!");
//            } else {
//                careerStats.setVisible(false);
//                seasonStats.setVisible(true);
//                label.setText(controlPanel.getSeason());
//                button.setText("See Career Stats!");
//            }
//        });
//        button.setAlignment(Pos.CENTER);
//        return button;
//    }
//
//    private Button makeSeeDifferenceButton(StatView firstPlayerStatView, StatView secondPlayerStatView){
//        Button button = new Button("See Difference!");
//        button.setOnAction(event -> {
//            PlayerStats firstPlayerStats = firstPlayerStatView.getPlayerStats();
//            PlayerStats secondPlayerStats = secondPlayerStatView.getPlayerStats();
//            for (int i = 0; i < firstPlayerStats.getSeasonStatsList().size(); i++){
//                ComparePlayerStat comparePlayerStat = ComparePlayerStat
//                        .withFirstPlayerStats(firstPlayerStats).andSecondPlayerStats(secondPlayerStats);
//                float difference = comparePlayerStat.getDifferencesAsList().get(i);
//                int flag = comparePlayerStat.getFlag();
//                if(flag == comparePlayerStat.FIRST_PLAYER_IS_GREATER){
//                    highlightLabelGreen(firstPlayerStatView.getPpgLabel());
//                    highlightLabelRed(secondPlayerStatView.getPpgLabel());
//                }else if(flag == comparePlayerStat.SECOND_PLAYER_IS_GREATER){
//                    highlightLabelRed(firstPlayerStatView.getPpgLabel());
//                    highlightLabelGreen(secondPlayerStatView.getPpgLabel());
//                }
//            }
//        });
//        return button;
//    }
//
//    private void getFirstHeadshotLogoView() {
//        try {
//            firstHeadshotLogoView = HeadshotLogoView.withTeam(firstControlPanel.getTeam())
//                    .andPlayerName(firstControlPanel.getPlayer());
//            firstHeadshotLogoView.generateHeadshot();
//            firstHeadshotLogoView.generateLogo();
//            firstHeadshotLogoView.formatFirstPane();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void getSecondHeadshotLogoView() {
//        try {
//            secondHeadshotLogoView = HeadshotLogoView.withTeam(secondControlPanel.getTeam())
//                    .andPlayerName(secondControlPanel.getPlayer());
//            secondHeadshotLogoView.generateHeadshot();
//            secondHeadshotLogoView.generateLogo();
//            secondHeadshotLogoView.formatSecondPane();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void highlightLabelGreen(Label label){
//        label.setBackground(new Background(new BackgroundFill(Color.LAWNGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
//    }
//
//    private void highlightLabelRed(Label label){
//        label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//    }
//}
