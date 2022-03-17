package main.dcs_installer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.*;


public class GUI extends Application {

    private Stage stage;
    private Scene scene;
    private BorderPane bPane;
    private String InstallationFolder = "";
    private String SavedGamesFolder = "";
    private String userDir = System.getProperty("user.dir");
    private CheckBox migotomod;
    private CheckBox smokemod;
    private CheckBox simplexmod;
    private CheckBox fsrmod;
    private CheckBox sufa;
    private CheckBox desdemicabina;
    private CheckBox su30;
    private CheckBox allskins;
    public static String error = "";

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        Image logo = new Image("logo.png");
        stage.getIcons().add(logo);

        BorderPane bPane = new BorderPane();
        this.bPane = bPane;

        Scene scene = new Scene(bPane);
        this.scene = scene;
        stage.setTitle("Instalador Mods DCS por Termi");
        stage.setResizable(false);

        stage.setWidth(1400);
        stage.setHeight(800);

        stage.setScene(scene);

        stage.show();

        start_GUI1();
    }

    private void start_GUI1(){
        createTop_GUI1(bPane);
        createCenter_GUI1(bPane);
        createBottom_GUI1(bPane);

        scene.getStylesheets().add("GUI-1-styles.css");
    }

    private void createTop_GUI1(BorderPane bPane){
        bPane.setBackground(new Background(
                new BackgroundImage(
                        new Image("f18.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                ))
        );

        Label title = new Label("Instalador de mods DCS por Termi");
        title.getStyleClass().add("top");
        bPane.setTop(title);
    }

    private void createCenter_GUI1(BorderPane bPane){
        VBox centerBox = new VBox();
        centerBox.getStyleClass().add("center");

        HBox centertopBox = new HBox();
        centertopBox.getStyleClass().add("centerBoxs");
        HBox centerbottomBox = new HBox();
        centerbottomBox.getStyleClass().add("centerBoxs");

        Label InstallationFolderTitle = new Label("Seleccionar carpeta de instalación:");
        InstallationFolderTitle.getStyleClass().add("FoldersTitles");

        Button InstallationFolderButton = new Button("Seleccionar carpeta");
        InstallationFolderButton.getStyleClass().add("FolderButtons");
        InstallationFolderButton.setOnAction((EventHandler<javafx.event.ActionEvent>) event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if(selectedDirectory != null) {
                InstallationFolder = selectedDirectory.getAbsolutePath();
            }
        });

        Label SavedGamesFolderTitle = new Label("Seleccionar carpeta de juegos guardados:");
        SavedGamesFolderTitle.getStyleClass().add("FoldersTitles");

        Button SavedGamesFolderButton = new Button("Seleccionar carpeta");
        SavedGamesFolderButton.getStyleClass().add("FolderButtons");
        SavedGamesFolderButton.setOnAction((EventHandler<javafx.event.ActionEvent>) event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            if(selectedDirectory != null){
                SavedGamesFolder = selectedDirectory.getAbsolutePath();
            }
        });

        centertopBox.getChildren().add(InstallationFolderTitle);
        centertopBox.getChildren().add(InstallationFolderButton);
        centerbottomBox.getChildren().add(SavedGamesFolderTitle);
        centerbottomBox.getChildren().add(SavedGamesFolderButton );

        centerBox.getChildren().add(centertopBox);
        centerBox.getChildren().add(centerbottomBox);

        bPane.setCenter(centerBox);
    }

    private void createBottom_GUI1(BorderPane bPane){
        BorderPane bottomBorderPane = new BorderPane();
        HBox bottomcenterBox = new HBox();

        bottomcenterBox.getStyleClass().add("bottom");

        Button CancelButton = new Button("Salir");
        CancelButton.getStyleClass().add("BottomButtons");
        CancelButton.setOnAction((EventHandler<javafx.event.ActionEvent>) event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING,null, ButtonType.YES, ButtonType.CANCEL);
            alert.getDialogPane().setHeaderText("¿Está seguro de que deseas salir?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                Platform.exit();
                System.exit(0);
            }
        });

        Button NextButton = new Button("Siguiente");
        NextButton.getStyleClass().add("BottomButtons");
        NextButton.setOnAction((EventHandler<javafx.event.ActionEvent>) event -> {
            if(InstallationFolder.equals("") && SavedGamesFolder.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                alert.getDialogPane().setHeaderText("No ha seleccionado la carpeta de instalación ni la de juegos guardados");
                alert.showAndWait();
            }
            else if(InstallationFolder.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                alert.getDialogPane().setHeaderText("No ha seleccionado la carpeta de instalación");
                alert.showAndWait();
            }
            else if(SavedGamesFolder.equals("")){
                Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                alert.getDialogPane().setHeaderText("No ha seleccionado la carpeta de juegos guardados");
                alert.showAndWait();
            }
            else{

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.CANCEL);
                alert.getDialogPane().setHeaderText("Su carpeta de instalación es: \n" + InstallationFolder + "\n"
                        + "Su carpeta de juegos guardados es: " + "\n" + SavedGamesFolder + "\n"
                        + "¿Está seguro de que son correctas y desea continuar?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    start_GUI2();
                }
            }
        });

        bottomBorderPane.setCenter(bottomcenterBox);
        bottomBorderPane.setLeft(CancelButton);
        bottomBorderPane.setRight(NextButton);

        bPane.setBottom(bottomBorderPane);
    }

    private void createTop_GUI2(BorderPane bPane){
        bPane.setBackground(new Background(
                new BackgroundImage(
                        new Image("f18_2.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                ))
        );

        HBox box = new HBox();
        Label title = new Label("Seleccione qué desea instalar");
        title.getStyleClass().add("top");

        VBox centerBox = new VBox();
        centerBox.getStyleClass().add("center2");

        HBox modsBox = new HBox();
        modsBox.getStyleClass().add("modsBox");

        VBox modsListBox = new VBox();
        modsListBox.getStyleClass().add("modsList");

        HBox skinsBox = new HBox();
        skinsBox.getStyleClass().add("skinsBox");

        VBox skinsListBox = new VBox();
        skinsListBox.getStyleClass().add("modsList");


        Label ModsInstallationTitle = new Label("Instalar Mods:");
        ModsInstallationTitle.setTooltip(
                new Tooltip("Mejoran la calidad visual y el rendimiento")
        );
        ModsInstallationTitle.getStyleClass().add("ModsTitle");

        Label migototext = new Label("3DMigoto");
        migotomod = new CheckBox("3DMigoto");
        migotomod.setTooltip(
                new Tooltip("Mejora la calidad de las etiquetas en la cabina aplicando filtros de escalado.")
        );
        smokemod = new CheckBox("Better Smoke");
        smokemod.setTooltip(
                new Tooltip("Los humos son mas realistas y grandes.")
        );
        simplexmod = new CheckBox("Simplex");
        simplexmod.setTooltip(
                new Tooltip("Mejora el rendimiento modificando los shaders.")
        );
        fsrmod = new CheckBox("FSR");
        fsrmod.setTooltip(
                new Tooltip("Mejora el rendimiento. Solo VR.")
        );
        sufa = new CheckBox("F-16I Sufa");
        sufa.setTooltip(
                new Tooltip("Añade el F-16I Sufa junto con la bomba Spice 2000.")
        );
        su30 = new CheckBox("Su-30");
        su30.setTooltip(
                new Tooltip("Añade el Su-30.")
        );
        desdemicabina = new CheckBox("Desde mi cabina");
        desdemicabina.setTooltip(
                new Tooltip("Añade el poortaviones L61 y diferentes fragatas.")
        );

        Label SkinsInstallationTitle = new Label("Instalar Skins:");
        SkinsInstallationTitle.setTooltip(
                new Tooltip("Añade diferentes aspectos de pintura")
        );
        SkinsInstallationTitle.getStyleClass().add("SkinsTitle");

        allskins = new CheckBox("Todas");
        allskins.setTooltip(
                new Tooltip("Añade diferentes aspectos de pintura")
        );

        centerBox.getChildren().addAll(modsBox, modsListBox, skinsBox, skinsListBox);

        modsListBox.getChildren().addAll(migotomod, smokemod, simplexmod, fsrmod, sufa, su30, desdemicabina);

        modsBox.getChildren().addAll(ModsInstallationTitle);
        skinsBox.getChildren().addAll(SkinsInstallationTitle);
        skinsListBox.getChildren().addAll(allskins);



        //bPane.setRight(centerBox);


        box.getChildren().add(title);
        box.getChildren().add(centerBox);
        bPane.setTop(box);
    }

    private void createBottom_GUI2(BorderPane bPane){
        BorderPane bottomBorderPane = new BorderPane();
        HBox bottomcenterBox = new HBox();

        bottomcenterBox.getStyleClass().add("bottom");

        Button CancelButton = new Button("Atras");
        CancelButton.getStyleClass().add("BottomButtons");
        CancelButton.setOnAction((EventHandler<javafx.event.ActionEvent>) event -> {
            bPane.setRight(null);
            bPane.setTop(null);
            start_GUI1();
        });

        Button NextButton = new Button("Siguiente");
        NextButton.getStyleClass().add("BottomButtons");
        NextButton.setOnAction(event -> {

            if(!allskins.isSelected() && !migotomod.isSelected() && !smokemod.isSelected() && !simplexmod.isSelected() && !fsrmod.isSelected() && !sufa.isSelected() && !su30.isSelected() && !desdemicabina.isSelected()){
                Alert alert = new Alert(Alert.AlertType.WARNING, null, ButtonType.OK);
                alert.getDialogPane().setHeaderText("No ha seleccionado nada que instalar");
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, null, ButtonType.YES, ButtonType.CANCEL);
                alert.getDialogPane().setHeaderText("¿Desea continuar?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    start_GUI3();

                    Task<Boolean> task = new Task<Boolean>() {
                        @Override
                        public Boolean call() {
                            if(installSkins() && installMods()){
                                return true;
                            }
                            return false;
                        }
                    };

                    task.setOnSucceeded(e -> {

                        boolean result = task.getValue();
                        if(result){
                            Alert completealert = new Alert(Alert.AlertType.INFORMATION,null, ButtonType.YES);
                            completealert.getDialogPane().setHeaderText("Instalación completada");
                            completealert.getDialogPane().setContentText(error);
                            completealert.showAndWait();
                            start_GUI4();
                        }
                        else{
                            Alert completealert = new Alert(Alert.AlertType.ERROR,null, ButtonType.OK);
                            completealert.getDialogPane().setHeaderText("Ha habido un error durante la instalación");
                            completealert.getDialogPane().setContentText(error);
                            completealert.showAndWait();
                            Platform.exit();
                            System.exit(0);
                        }

                    });

                    new Thread(task).start();
                }
            }
        });

        bottomBorderPane.setCenter(bottomcenterBox);
        bottomBorderPane.setLeft(CancelButton);
        bottomBorderPane.setRight(NextButton);

        bPane.setBottom(bottomBorderPane);
    }

    private void createTop_GUI3(BorderPane bPane){
        bPane.setBackground(new Background(
                new BackgroundImage(
                        new Image("f16.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                ))
        );

        Label title = new Label("Instalando, por favor espere");
        title.getStyleClass().add("top");
        bPane.setTop(title);
    }

    private void createTop_GUI4(BorderPane bPane){
        bPane.setBackground(new Background(
                new BackgroundImage(
                        new Image("wallpaper.jpg"),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                        new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                ))
        );

        Label title = new Label("Gracias por usar mi instalador");
        title.getStyleClass().add("top");
        bPane.setTop(title);
    }

    private void createBottom_GUI4(BorderPane bPane){
        BorderPane bottomBorderPane = new BorderPane();
        HBox bottomcenterBox = new HBox();

        bottomcenterBox.getStyleClass().add("bottom");

        Button NextButton = new Button("Salir");
        NextButton.getStyleClass().add("BottomButtons");
        NextButton.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });

        bottomBorderPane.setCenter(bottomcenterBox);
        bottomBorderPane.setRight(NextButton);

        bPane.setBottom(bottomBorderPane);
    }

    private void start_GUI2(){

        createTop_GUI2(bPane);
        bPane.setCenter(null);
        createBottom_GUI2(bPane);
        scene.getStylesheets().add("GUI-2-styles.css");
    }

    private void start_GUI3(){

        createTop_GUI3(bPane);
        bPane.setCenter(null);
        bPane.setRight(null);
        bPane.setBottom(null);


        scene.getStylesheets().add("GUI-3-styles.css");
    }

    private void start_GUI4(){

        createTop_GUI4(bPane);
        createBottom_GUI4(bPane);

        scene.getStylesheets().add("GUI-4-styles.css");
    }

    private boolean installMods(){
        String path = ".";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(migotomod.isSelected()){
            if(Utils.unzipFile(path + "/3Dmigoto.zip", InstallationFolder)){
                Utils.removeFile(SavedGamesFolder + "/fxo");
                Utils.removeFile(SavedGamesFolder + "/metashaders2");
            }
            else{
                return false;
            }
        }
        if(smokemod.isSelected()){
            if(!Utils.unzipFile(path + "/Smoke.zip", InstallationFolder))
                return false;
        }
        if(simplexmod.isSelected()){
            if(Utils.unzipFile(path + "/SIMPLEX.zip", InstallationFolder)){
                Utils.removeFile(SavedGamesFolder + "/fxo");
                Utils.removeFile(SavedGamesFolder + "/metashaders2");
            }
            else{
                return false;
            }
        }
        if(fsrmod.isSelected()){
            if(!Utils.unzipFile(path + "/vrperfkit.zip", InstallationFolder))
                return false;
        }
        if(sufa.isSelected()){
            Utils.removeFile(InstallationFolder + "/CoreMods/aircraft/F-16C/Liveries/F-16C_50");
            if(!Utils.unzipFile(path + "/F16Sufa.zip", InstallationFolder))
                return false;
        }
        if(su30.isSelected()) {
            if (!Utils.unzipFile(path + "/Su30.zip", SavedGamesFolder)) {
                return false;
            }
        }
        if(desdemicabina.isSelected()){
            if(!Utils.unzipFile(path + "/DesdeMiCabina.zip", SavedGamesFolder))
                return false;
        }

        return true;
    }

    private boolean installSkins(){
        boolean result = true;
        String path = ".";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if(allskins.isSelected()){
            if(!Utils.unzipFile(path + "/Skins.zip", SavedGamesFolder))
                return false;
        }
        return true;
    }
}