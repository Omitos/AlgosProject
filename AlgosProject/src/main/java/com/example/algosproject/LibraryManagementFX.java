package com.example.algosproject;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Optional;


public class LibraryManagementFX extends Application {


    private ObservableList<String[]> books = FXCollections.observableArrayList();

    private TextField textField1 = new TextField();
    private TextField textField2 = new TextField();
    private TextField textField3 = new TextField();
    private TextField textField4 = new TextField();
    private TextField textField5 = new TextField();
    private TextField textField6 = new TextField();
    private TextField textField7 = new TextField();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        Label label1 = new Label("Book ID");
        Label label2 = new Label("Book Title");
        Label label3 = new Label("Author");
        Label label4 = new Label("Publisher");
        Label label5 = new Label("Year of Publication");
        Label label6 = new Label("ISBN");
        Label label7 = new Label("Number of Copies");

        Button addButton = new Button("Add");
        Button viewButton = new Button("View");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        Button exitButton = new Button("Exit");
        Button sortingButton = new Button("Sort");
        Button algorithmButton = new Button("Search");

        addButton.setOnAction(e -> handleAddButton());
        viewButton.setOnAction(e -> handleViewButton());
        editButton.setOnAction(e -> handleEditButton());
        deleteButton.setOnAction(e -> handleDeleteButton());
        clearButton.setOnAction(e -> clearFields());
        exitButton.setOnAction(e -> System.exit(0));
        sortingButton.setOnAction(e -> handleSorting());
        algorithmButton.setOnAction(e -> handleAlgorithm());

        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        grid.add(label1, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(textField2, 1, 1);
        grid.add(label3, 0, 2);
        grid.add(textField3, 1, 2);
        grid.add(label4, 0, 3);
        grid.add(textField4, 1, 3);
        grid.add(label5, 0, 4);
        grid.add(textField5, 1, 4);
        grid.add(label6, 0, 5);
        grid.add(textField6, 1, 5);
        grid.add(label7, 0, 6);
        grid.add(textField7, 1, 6);

        HBox buttonBox = new HBox(10);
        buttonBox.getStyleClass().add("button-box");
        buttonBox.getChildren().addAll(addButton, viewButton, editButton, deleteButton, clearButton, exitButton, sortingButton, algorithmButton);

        VBox container = new VBox(15);
        container.getStyleClass().add("container");
        container.getChildren().addAll(grid, buttonBox);
        container.setPrefSize(400, 550);

        Scene scene = new Scene(container);
        primaryStage.setScene(scene);


        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());


        primaryStage.show();
    }

    private void handleAddButton() {
        String[] book = new String[7];
        book[0] = textField1.getText();
        book[1] = textField2.getText();
        book[2] = textField3.getText();
        book[3] = textField4.getText();
        book[4] = textField5.getText();
        book[5] = textField6.getText();
        book[6] = textField7.getText();
        books.add(book);
        showAlert("Book added successfully");
        clearFields();
    }

    private void handleViewButton() {
        displayBooks("View Books", books);
    }

    private void handleEditButton() {
        String bookID = getInput("Enter book ID to edit:");
        for (String[] book : books) {
            if (book[0].equals(bookID)) {
                editBook(book);
                showAlert("Book edited successfully");
                clearFields();
                return;
            }
        }
        showAlert("Book not found");
    }

    private void handleDeleteButton() {
        String bookID = getInput("Enter book ID to delete:");
        for (String[] book : books) {
            if (book[0].equals(bookID)) {
                books.remove(book);
                showAlert("Book deleted successfully");
                clearFields();
                return;
            }
        }
        showAlert("Book not found");
    }

    private void handleSorting() {
        bubbleSort(books);
        displayBooks("Books Sorted by Bubble Sort", books);
    }

    private void handleAlgorithm() {
        String searchKey = getInput("Enter book title to search:");
        int resultIndex = binarySearch(books, searchKey);
        if (resultIndex != -1) {
            displayBooks("Search Results", FXCollections.observableArrayList(books.subList(resultIndex, resultIndex + 1)));
        } else {
            showAlert("Book not found: " + searchKey);
        }
    }


    private void displayBooks(String title, ObservableList<String[]> books) {
        String[] columns = {"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"};
        Object[][] data = new Object[books.size()][7];
        for (int i = 0; i < books.size(); i++) {
            System.arraycopy(books.get(i), 0, data[i], 0, 7);
        }
        TableView<Object[]> table = new TableView<>();
        for (int i = 0; i < columns.length; i++) {
            final int finalIdx = i;
            TableColumn<Object[], String> column = new TableColumn<>(columns[i]);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()[finalIdx].toString()));
            table.getColumns().add(column);
        }
        table.getItems().addAll(Arrays.asList(data));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(table, 800, 400));
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String getInput(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input");
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void editBook(String[] book) {
        book[1] = textField2.getText();
        book[2] = textField3.getText();
        book[3] = textField4.getText();
        book[4] = textField5.getText();
        book[5] = textField6.getText();
        book[6] = textField7.getText();
    }

    private void clearFields() {
        textField1.clear();
        textField2.clear();
        textField3.clear();
        textField4.clear();
        textField5.clear();
        textField6.clear();
        textField7.clear();
    }

    private void bubbleSort(ObservableList<String[]> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(j)[1].compareTo(books.get(j + 1)[1]) > 0) {
                    // swap temp and arr[i]
                    String[] temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
    }

    private int binarySearch(ObservableList<String[]> books, String searchTerm) {
        int low = 0;
        int high = books.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = books.get(mid)[1].compareTo(searchTerm);

            if (comparison == 0) {
                return mid; // found
            } else if (comparison < 0) {
                low = mid + 1; // search in the right half
            } else {
                high = mid - 1; // search in the left half
            }
        }

        return -1; // not found
    }
}
