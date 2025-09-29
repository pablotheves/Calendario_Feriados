
package com.cadm.feriados;

import addon.DateUtils;
import cadm.feriados.dao.FeriadosDao;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.classes.Feriado;
import model.exceptions.ValidacaoException;
import model.services.FeriadosService;

public class PrimaryController implements Initializable {

    private Feriado feriado;

    public void setFeriado(Feriado feriado) {
        this.feriado = feriado;

    }

    @FXML
    private TableView<Feriado> tableViewFeriados;

    @FXML
    private TableColumn<Feriado, Integer> tableColumnCod;

    @FXML
    private TableColumn<Feriado, String> tableColumnNome;

    @FXML
    private Label lblErroNome;

    @FXML
    private TableColumn<Feriado, LocalDate> tableColumnData;

    @FXML
    private Label lblErroData;

    @FXML
    private TableColumn<Feriado, String> tableColumnTipo;

    @FXML
    private Label lblErroTipo;

    @FXML
    private Button btnCalc;

    @FXML
    private DatePicker txtDataInicial;

    @FXML
    private DatePicker txtDataFinal;

    @FXML
    private Label diasUteis;

    @FXML
    private DatePicker dataFeriado;

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField tipo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnDeletar;

    private ObservableList<Feriado> listaTabela;

    @FXML
    protected void tbFeriadoClique(MouseEvent e) {
        Feriado selectedFeriado = tableViewFeriados.getSelectionModel().getSelectedItem();
        if (selectedFeriado != null) {
            this.feriado = selectedFeriado; 
            
            txtDescricao.setText(feriado.getNome());
            tipo.setText(feriado.getTipo());

            if (feriado.getDataFeriado() != null) {
                LocalDate localDate = ((java.sql.Date) feriado.getDataFeriado()).toLocalDate();
                dataFeriado.setValue(localDate);
            }
        }
    }

    @FXML
    protected void btSalvarFeriadoClique(MouseEvent e) {

    }

    @FXML
    protected void btDeletarFeriadoClique(MouseEvent e) {

    }

    @FXML
    void calc(ActionEvent event) {//calculo dos dias uteis

        if (txtDataInicial.getValue() == null || txtDataFinal.getValue() == null) {
            diasUteis.setText("Datas inválidas!");
            return;
        }

        LocalDate dataInicial = txtDataInicial.getValue();
        LocalDate dataFinal = txtDataFinal.getValue();
        FeriadosService service = new FeriadosService();
        List<Feriado> listaFeriados = service.getAll();

        java.sql.Date ldataInicial = java.sql.Date.valueOf(dataInicial);//tive que fazer um local data para conseguir utilizar o datePicker
        java.sql.Date ldataFinal = java.sql.Date.valueOf(dataFinal);

        String calculo = String.valueOf(DateUtils.getWorkingDays(ldataInicial, ldataFinal, listaFeriados));
        diasUteis.setText(calculo);
    }

    public void atualizarTabela() {
        // associando lista a tabela utilizando um ObservableList
        listaTabela = FXCollections.observableArrayList(new FeriadosService().getAll());
        tableViewFeriados.setItems(listaTabela);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // configurando cada coluna
        tableColumnCod.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("dataFeriado"));

        atualizarTabela();

        btnSalvar.setOnAction((t) -> {

            try {

                ValidacaoException exc = new ValidacaoException("Erro Validacao!");

                if (feriado == null) {
                    feriado = new Feriado();
                }

                if (txtDescricao.getText() == null) {
                    exc.adicionarErro("Descricao", "O campo não pode ser vazio!");
                } else if (txtDescricao.getText().equals("")) {
                    exc.adicionarErro("Descricao", "O campo não pode ser vazio!");
                } else {
                    feriado.setNome(txtDescricao.getText());
                }

                if (dataFeriado.getValue() == null) {
                    exc.adicionarErro("Data", "O campo não pode ser vazio!");
                } else {
                    LocalDate localDate = dataFeriado.getValue();
                    java.sql.Date dataSql = java.sql.Date.valueOf(localDate);

                    feriado.setDataFeriado(dataSql);
                }

                if (tipo.getText() == null) {
                    exc.adicionarErro("Tipo", "O campo não pode ser vazio!");
                } else if (tipo.getText().equals("")) {
                    exc.adicionarErro("Tipo", "O campo não pode ser vazio!");
                } else {
                    feriado.setTipo(tipo.getText());
                }

                if (!exc.getErrors().isEmpty()) {
                    throw exc;
                }
                //atualiza no banco
                FeriadosService service = new FeriadosService();
                service.salvarOuAtualizar(feriado);

                // atualizar tabela após salvar
                atualizarTabela();

            } catch (ValidacaoException ev) {
                setErrorMessages(ev.getErrors());
            }

        });

        btnDeletar.setOnAction((t) -> {
            if (tableViewFeriados.getSelectionModel().getSelectedItem() != null) {
                Feriado feriado = tableViewFeriados.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmação");
                alert.setHeaderText(null);
                alert.setContentText(feriado.getNome() + " será excluido! Tem certeza?");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    if (new FeriadosService().excluir(feriado)) {
                        Alert mens = new Alert(Alert.AlertType.INFORMATION);
                        mens.setTitle("Excluído");
                        mens.setHeaderText(null);
                        mens.setContentText("Registro excluído!");
                        mens.showAndWait();
                        atualizarTabela();
                    }
                }
            }

        });
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        lblErroData.setText(fields.contains("Data") ? errors.get("Data") : "");
        lblErroNome.setText(fields.contains("Descricao") ? errors.get("Descricao") : "");
        lblErroTipo.setText(fields.contains("Tipo") ? errors.get("Tipo") : "");

    }

}
