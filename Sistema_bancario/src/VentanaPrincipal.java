import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class VentanaPrincipal extends Application {

    // ── Componentes ───────────────────────────────────────────────────
    private TextField    txtNroCuenta  = new TextField();
    private TextField    txtSaldo      = new TextField();
    private ListView<Cuenta> listaCuentas = new ListView<>();
    private TextArea     areaReporte   = new TextArea();
    private TextField    txtMonto      = new TextField();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Examen Programacion II");

        // ── PANEL IZQUIERDO: Registro ─────────────────────────────────
        Label lblRegistro  = new Label("Registro de cuentas");
        lblRegistro.setStyle("-fx-font-style: italic;");
        Label lblNro    = new Label("Nro. de cuenta:");
        Label lblSaldo  = new Label("Saldo Inicial:");
        Button btnRegistrar = new Button("Registrar");
        Button btnLimpiar   = new Button("Limpiar");
        Label lblCuentas    = new Label("Cuentas");
        lblCuentas.setStyle("-fx-font-style: italic;");
        Button btnVerMov    = new Button("Ver Movimientos");

        HBox filaBotones = new HBox(5, btnRegistrar, btnLimpiar);

        VBox panelIzq = new VBox(6,
            lblRegistro,
            lblNro, txtNroCuenta,
            lblSaldo, txtSaldo,
            filaBotones,
            lblCuentas,
            listaCuentas,
            btnVerMov
        );
        panelIzq.setPrefWidth(200);
        panelIzq.setPadding(new Insets(10));

        // ── PANEL CENTRO: Reporte ─────────────────────────────────────
        Label lblReporte = new Label("Reporte de movimientos");
        lblReporte.setStyle("-fx-font-style: italic;");
        areaReporte.setEditable(false);
        areaReporte.setPrefHeight(300);
        Button btnLimpiarReporte = new Button("Limpiar Reporte");
        Button btnVerSaldo       = new Button("Ver Saldo");
        HBox filaCentro = new HBox(5, btnLimpiarReporte, btnVerSaldo);

        VBox panelCentro = new VBox(6, lblReporte, areaReporte, filaCentro);
        panelCentro.setPrefWidth(300);
        panelCentro.setPadding(new Insets(10));

        // ── PANEL DERECHO: Retiro/Depósito ────────────────────────────
        Label lblRetDep = new Label("Retiro - Deposito");
        lblRetDep.setStyle("-fx-font-style: italic;");
        Label lblMonto  = new Label("Monto:");
        Button btnRetirar   = new Button("Retirar");
        Button btnDepositar = new Button("Depositar");
        btnRetirar.setMaxWidth(Double.MAX_VALUE);
        btnDepositar.setMaxWidth(Double.MAX_VALUE);

        VBox panelDer = new VBox(6,
            lblRetDep, lblMonto, txtMonto,
            btnRetirar, btnDepositar
        );
        panelDer.setPrefWidth(160);
        panelDer.setPadding(new Insets(10));

        // ── LAYOUT PRINCIPAL ──────────────────────────────────────────
        HBox root = new HBox(panelIzq, panelCentro, panelDer);
        Scene scene = new Scene(root, 700, 420);
        stage.setScene(scene);
        stage.show();

        // ── CARGAR CUENTAS AL INICIO ──────────────────────────────────
        cargarCuentas();

        // ── EVENTOS ───────────────────────────────────────────────────

        btnLimpiar.setOnAction(e -> {
            txtNroCuenta.clear();
            txtSaldo.clear();
        });

        btnRegistrar.setOnAction(e -> {
            try {
                // Validar que sean numéricos y positivos
                int    nro   = Integer.parseInt(txtNroCuenta.getText().trim());
                double saldo = Double.parseDouble(txtSaldo.getText().trim());

                if (nro < 0 || saldo < 0) {
                    alerta("Advertencia",
                           "Numero de cuenta y saldo inicial deben ser numeros positivos",
                           Alert.AlertType.WARNING);
                    return;
                }

                Cuenta nueva = new Cuenta(nro, saldo);
                nueva.registrar();
                cargarCuentas();
                btnLimpiar.fire();
                mensaje("Cuenta registrada correctamente.");

            } catch (NumberFormatException ex) {
                alerta("Advertencia",
                       "Numero de cuenta y saldo inicial deben ser numeros",
                       Alert.AlertType.WARNING);
            } catch (Exception ex) {
                alerta("Error", "Error al registrar: " + ex.getMessage(),
                       Alert.AlertType.ERROR);
            }
        });

        btnVerMov.setOnAction(e -> {
            Cuenta seleccionada = listaCuentas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                alerta("Advertencia", "Debe Seleccionar una cuenta", Alert.AlertType.WARNING);
                return;
            }
            try {
                seleccionada.cargarMovimientos();
                areaReporte.clear();
                for (Movimiento m : seleccionada.getMovimientos()) {
                    areaReporte.appendText(m.toString() + "\n");
                }
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnLimpiarReporte.setOnAction(e -> areaReporte.clear());

        btnVerSaldo.setOnAction(e -> {
            Cuenta seleccionada = listaCuentas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                alerta("Advertencia", "Debe Seleccionar una cuenta", Alert.AlertType.WARNING);
                return;
            }
            areaReporte.appendText("Saldo actual cuenta "
                + seleccionada.getNumeroCuenta() + ": "
                + seleccionada.getSaldo() + "\n");
        });

        btnRetirar.setOnAction(e -> {
            Cuenta seleccionada = listaCuentas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                alerta("Advertencia", "Debe Seleccionar una cuenta", Alert.AlertType.WARNING);
                return;
            }
            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                if (monto < 0) {
                    alerta("Advertencia", "El monto debe ser positivo", Alert.AlertType.WARNING);
                    return;
                }
                boolean ok = seleccionada.retirar(monto);
                if (ok) {
                    mensaje("Retiro exitoso. Nuevo saldo: " + seleccionada.getSaldo());
                    actualizarMovimientosEnLista(seleccionada);
                } else {
                    alerta("Advertencia",
                           "Saldo insuficiente o monto inválido",
                           Alert.AlertType.WARNING);
                }
            } catch (NumberFormatException ex) {
                alerta("Advertencia", "El monto debe ser un número", Alert.AlertType.WARNING);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnDepositar.setOnAction(e -> {
            Cuenta seleccionada = listaCuentas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                alerta("Advertencia", "Debe Seleccionar una cuenta", Alert.AlertType.WARNING);
                return;
            }
            try {
                double monto = Double.parseDouble(txtMonto.getText().trim());
                if (monto < 0) {
                    alerta("Advertencia", "El monto debe ser positivo", Alert.AlertType.WARNING);
                    return;
                }
                boolean ok = seleccionada.depositar(monto);
                if (ok) {
                    mensaje("Depósito exitoso. Nuevo saldo: " + seleccionada.getSaldo());
                    actualizarMovimientosEnLista(seleccionada);
                } else {
                    alerta("Error", "No se pudo realizar el depósito", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                alerta("Advertencia", "El monto debe ser un número", Alert.AlertType.WARNING);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    // ── MÉTODOS AUXILIARES ────────────────────────────────────────────

    private void cargarCuentas() {
        try {
            ArrayList<Cuenta> cuentas = Cuenta.leerCuentas();
            listaCuentas.getItems().setAll(cuentas);
        } catch (Exception e) {
            alerta("Error", "No se pudieron cargar las cuentas: " + e.getMessage(),
                   Alert.AlertType.ERROR);
        }
    }

    // Refresca el reporte si la cuenta seleccionada hizo un movimiento
    private void actualizarMovimientosEnLista(Cuenta c) {
        try {
            areaReporte.clear();
            c.cargarMovimientos();
            for (Movimiento m : c.getMovimientos()) {
                areaReporte.appendText(m.toString() + "\n");
            }
        } catch (Exception e) {
            alerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void alerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mensaje(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensaje");
        alert.setHeaderText("Mensaje");
        alert.setContentText(texto);
        alert.showAndWait();
    }
}