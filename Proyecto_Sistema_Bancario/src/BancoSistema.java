import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.util.ArrayList;

public class BancoSistema extends Application {

    private static Banco banco;

    public static void setBanco(Banco bancoInstancia) {
        banco = bancoInstancia;
    }

    // VARIABLES CLIENTE
    
    private TextField txtBuscarCliente = new TextField();
    private TextArea areaBuscarCliente = new TextArea();

    private TextField txtConsultaId = new TextField();
    private TextArea areaConsultas = new TextArea();

    private TextField txtOperacionId = new TextField();
    private TextField txtOperacionCuenta = new TextField();
    private TextField txtOperacionMonto = new TextField();
    private TextField txtOperacionDestino = new TextField();

    // VARIABLES EMPLEADO
    
    private TextField txtNombre = new TextField();
    private TextField txtApellido = new TextField();
    private TextField txtId = new TextField();
    private TextField txtCorreo = new TextField();
    private TextField txtTelefono = new TextField();
    private PasswordField txtPassword = new PasswordField();
    private TextField txtNumeroCliente = new TextField();

    private TextField txtCuentaId = new TextField();
    private TextField txtCuentaNumero = new TextField();
    private TextField txtCuentaSaldo = new TextField();

    private ComboBox<String> cmbTipoCuenta = new ComboBox<>();

    private TextField txtGestionId = new TextField();
    private TextField txtGestionCuenta = new TextField();

    private TextArea areaResumen = new TextArea();
    private TextArea areaClientes = new TextArea();
    private TextArea areaCuentas = new TextArea();
    private TextArea areaMovimientos = new TextArea();

   
    @Override
    public void start(Stage stage) {

        stage.setTitle("Sistema Bancario ");

        // BOTONES DE ACCIÓN 
       
        Button btnBuscarCliente = new Button("Buscar Cliente");
        btnBuscarCliente.setMaxWidth(Double.MAX_VALUE);

        Button btnVerSaldo = new Button("Ver Saldo");
        Button btnVerMovimientos = new Button("Ver Movimientos");

        Button btnDepositar = new Button("Depositar");
        Button btnRetirar = new Button("Retirar");
        Button btnTransferir = new Button("Transferir");

        Button btnRegistrarCliente = new Button("Registrar Cliente");
        btnRegistrarCliente.setMaxWidth(Double.MAX_VALUE);

        Button btnCrearCuenta = new Button("Crear Cuenta");
        btnCrearCuenta.setMaxWidth(Double.MAX_VALUE);

        Button btnBloquear = new Button("Bloquear");
        Button btnDesbloquear = new Button("Desbloquear");

        Button btnResumen = new Button("Actualizar Resumen");
        btnResumen.setMaxWidth(Double.MAX_VALUE);
        Button btnVerClientes = new Button("Ver Clientes");
        btnVerClientes.setMaxWidth(Double.MAX_VALUE);
        Button btnVerCuentas = new Button("Ver Cuentas");
        btnVerCuentas.setMaxWidth(Double.MAX_VALUE);
        Button btnTodosMovimientos = new Button("Ver Todos los Movimientos");
        btnTodosMovimientos.setMaxWidth(Double.MAX_VALUE);

       
        cmbTipoCuenta.getItems().addAll("Cuenta Ahorros", "Cuenta Corriente");
        cmbTipoCuenta.setValue("Cuenta Ahorros");
        cmbTipoCuenta.setMaxWidth(Double.MAX_VALUE);

        
        txtBuscarCliente.setPromptText("ID Cliente...");
        txtConsultaId.setPromptText("ID Cliente...");
        txtOperacionId.setPromptText("ID Cliente...");
        txtOperacionCuenta.setPromptText("Nº Cuenta...");
        txtOperacionMonto.setPromptText("Monto ($)...");
        txtOperacionDestino.setPromptText("Nº Cuenta Destino...");
        txtCuentaId.setPromptText("ID Cliente...");
        txtCuentaNumero.setPromptText("Nº Cuenta...");
        txtCuentaSaldo.setPromptText("Saldo Inicial...");
        txtGestionId.setPromptText("ID Cliente...");
        txtGestionCuenta.setPromptText("Nº Cuenta...");

       
        areaBuscarCliente.setEditable(false);
        areaBuscarCliente.setPrefHeight(100);
        areaConsultas.setEditable(false);
        VBox.setVgrow(areaConsultas, Priority.ALWAYS);
        areaResumen.setEditable(false);
        areaResumen.setPrefHeight(80);
        areaClientes.setEditable(false);
        VBox.setVgrow(areaClientes, Priority.ALWAYS);
        areaCuentas.setEditable(false);
        VBox.setVgrow(areaCuentas, Priority.ALWAYS);
        areaMovimientos.setEditable(false);
        VBox.setVgrow(areaMovimientos, Priority.ALWAYS);

        // ESTRUCTURA DEL PANEL CLIENTE 
 
        // Columna Izquierda
        
        VBox colClienteIzq = new VBox(10);
        colClienteIzq.setPadding(new Insets(10));
        colClienteIzq.setPrefWidth(320);
        
        VBox seccionBuscar = new VBox(5, new Label("BUSCAR CLIENTE"), txtBuscarCliente, btnBuscarCliente, areaBuscarCliente);
        
        HBox filaBotonesConsulta = new HBox(10, btnVerSaldo, btnVerMovimientos);
        VBox seccionConsultas = new VBox(5, new Label("CONSULTAS"), txtConsultaId, filaBotonesConsulta);
        
        colClienteIzq.getChildren().addAll(seccionBuscar, new Separator(), seccionConsultas);

        // Columna Central
        
        VBox colClienteCentro = new VBox(8);
        colClienteCentro.setPadding(new Insets(10));
        colClienteCentro.setPrefWidth(320);
        
        HBox filaBotonesOperaciones = new HBox(5, btnDepositar, btnRetirar, btnTransferir);
        
        colClienteCentro.getChildren().addAll(
                new Label("OPERACIONES BANCARIAS"),
                new Label("ID Cliente:"), txtOperacionId,
                new Label("Número de Cuenta:"), txtOperacionCuenta,
                new Label("Monto ($):"), txtOperacionMonto,
                new Label("Cuenta Destino (Transferir):"), txtOperacionDestino,
                new Separator(),
                filaBotonesOperaciones
        );

        // Columna Derecha: Consola de Resultados
        
        VBox colClienteDer = new VBox(5);
        colClienteDer.setPadding(new Insets(10));
        colClienteDer.setPrefWidth(550);
        HBox.setHgrow(colClienteDer, Priority.ALWAYS);
        
        colClienteDer.getChildren().addAll(new Label("MONITOR DE RESULTADOS"), areaConsultas);

        // Contenedor Principal Pestaña Cliente
        
        HBox rootCliente = new HBox(15, colClienteIzq, new Separator(), colClienteCentro, new Separator(), colClienteDer);
        rootCliente.setPadding(new Insets(15));

     
        // ESTRUCTURA DEL PANEL EMPLEADO 
       

        // Columna Izquierda
        
        VBox colEmpleadoIzq = new VBox(6);
        colEmpleadoIzq.setPadding(new Insets(10));
        colEmpleadoIzq.setPrefWidth(320);
        
        colEmpleadoIzq.getChildren().addAll(
                new Label("REGISTRAR CLIENTE"),
                new Label("Nombre:"), txtNombre,
                new Label("Apellido:"), txtApellido,
                new Label("ID Identificación:"), txtId,
                new Label("Correo:"), txtCorreo,
                new Label("Teléfono:"), txtTelefono,
                new Label("Contraseña:"), txtPassword,
                new Label("Nº Único Cliente:"), txtNumeroCliente,
                new Separator(),
                btnRegistrarCliente
        );

        // Columna Central
        
        VBox colEmpleadoCentro = new VBox(10);
        colEmpleadoCentro.setPadding(new Insets(10));
        colEmpleadoCentro.setPrefWidth(320);

        VBox seccionCrearCuenta = new VBox(6,
                new Label("APERTURA DE CUENTA"),
                new Label("ID Cliente:"), txtCuentaId,
                new Label("Número Cuenta:"), txtCuentaNumero,
                new Label("Saldo Inicial ($):"), txtCuentaSaldo,
                new Label("Tipo Cuenta:"), cmbTipoCuenta,
                btnCrearCuenta
        );

        HBox filaBotonesGestion = new HBox(10, btnBloquear, btnDesbloquear);
        VBox seccionGestion = new VBox(6,
                new Label("BLOQUEO / GESTIÓN"),
                new Label("ID Cliente:"), txtGestionId,
                new Label("Número Cuenta:"), txtGestionCuenta,
                filaBotonesGestion
        );

        colEmpleadoCentro.getChildren().addAll(seccionCrearCuenta, new Separator(), seccionGestion);

        // Columna Derecha
        
        VBox colEmpleadoDer = new VBox(10);
        colEmpleadoDer.setPadding(new Insets(10));
        colEmpleadoDer.setPrefWidth(550);
        HBox.setHgrow(colEmpleadoDer, Priority.ALWAYS);

        VBox bloqueResumen = new VBox(3, new Label("Resumen General:"), btnResumen, areaResumen);
        VBox bloqueClientes = new VBox(3, new Label("Clientes:"), btnVerClientes, areaClientes);
        VBox bloqueCuentas = new VBox(3, new Label("Cuentas:"), btnVerCuentas, areaCuentas);
        VBox bloqueMovs = new VBox(3, new Label("Movimientos:"), btnTodosMovimientos, areaMovimientos);

        colEmpleadoDer.getChildren().addAll(bloqueResumen, bloqueClientes, bloqueCuentas, bloqueMovs);

        // Contenedor Principal 
        
        HBox rootEmpleado = new HBox(15, colEmpleadoIzq, new Separator(), colEmpleadoCentro, new Separator(), colEmpleadoDer);
        rootEmpleado.setPadding(new Insets(15));

        
        // PESTAÑAS 
       
        TabPane tabs = new TabPane();
        Tab tabCliente = new Tab(" Clientes ");
        Tab tabEmpleado = new Tab(" Empleados ");

        tabCliente.setClosable(false);
        tabEmpleado.setClosable(false);
        tabCliente.setContent(rootCliente);
        tabEmpleado.setContent(rootEmpleado);

        tabs.getTabs().addAll(tabCliente, tabEmpleado);

        
        Scene scene = new Scene(tabs, 1300, 750);
        stage.setScene(scene);
        stage.show();

        //EVENTOS 
    
        btnBuscarCliente.setOnAction(e -> {
            try {
                if (txtBuscarCliente.getText().trim().isEmpty()) {
                    alerta("Advertencia", "Ingrese el ID del cliente", Alert.AlertType.WARNING);
                    return;
                }
                Cliente c = banco.buscarCliente(txtBuscarCliente.getText().trim());
                areaBuscarCliente.setText(
                        "Nombre: " + c.getNombreCompleto()
                        + "\nCorreo: " + c.getCorreo()
                        + "\nTeléfono: " + c.getTelefono()
                        + "\nSaldo Total: " + c.getSaldoTotal()
                );
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnVerSaldo.setOnAction(e -> {
            try {
                if (txtConsultaId.getText().trim().isEmpty()) {
                    alerta("Advertencia", "Ingrese el ID del cliente", Alert.AlertType.WARNING);
                    return;
                }
                Cliente c = banco.buscarCliente(txtConsultaId.getText().trim());
                StringBuilder sb = new StringBuilder();
                sb.append("Cliente: ").append(c.getNombreCompleto()).append("\n\n");
                for (CuentaBancaria cuenta : c.getCuentas()) {
                    sb.append("Cuenta: ").append(cuenta.getNumeroCuenta())
                            .append(" | ").append(cuenta.getTipoCuenta())
                            .append(" | Saldo: ").append(cuenta.getSaldo())
                            .append("\n");
                }
                areaConsultas.setText(sb.toString());
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnVerMovimientos.setOnAction(e -> {
            try {
                areaConsultas.clear();
                ArrayList<Movimiento> movs = Movimiento.cargarMovimientos();
                for (Movimiento m : movs) {
                    areaConsultas.appendText(m.toString() + "\n");
                }
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnDepositar.setOnAction(e -> {
            try {
                validarOperacion();
                Cliente c = banco.buscarCliente(txtOperacionId.getText().trim());
                CuentaBancaria cuenta = c.buscarCuenta(Integer.parseInt(txtOperacionCuenta.getText().trim()));
                cuenta.depositar(Double.parseDouble(txtOperacionMonto.getText().trim()));
                banco.guardarDatos();
                alerta("Mensaje", "Depósito realizado con éxito", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnRetirar.setOnAction(e -> {
            try {
                validarOperacion();
                Cliente c = banco.buscarCliente(txtOperacionId.getText().trim());
                CuentaBancaria cuenta = c.buscarCuenta(Integer.parseInt(txtOperacionCuenta.getText().trim()));
                cuenta.retirar(Double.parseDouble(txtOperacionMonto.getText().trim()));
                banco.guardarDatos();
                alerta("Mensaje", "Retiro realizado con éxito", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnTransferir.setOnAction(e -> {
            try {
                validarOperacion();
                if (txtOperacionDestino.getText().trim().isEmpty()) {
                    alerta("Advertencia", "Ingrese cuenta destino", Alert.AlertType.WARNING);
                    return;
                }
                Cliente c = banco.buscarCliente(txtOperacionId.getText().trim());
                CuentaBancaria origen = c.buscarCuenta(Integer.parseInt(txtOperacionCuenta.getText().trim()));
                CuentaBancaria destino = null;

                for (Cliente cli : banco.getClientes()) {
                    destino = cli.buscarCuenta(Integer.parseInt(txtOperacionDestino.getText().trim()));
                    if (destino != null) { break; }
                }

                if (destino == null) {
                    alerta("Advertencia", "Cuenta destino no encontrada", Alert.AlertType.WARNING);
                    return;
                }

                banco.transferir(origen, destino, Double.parseDouble(txtOperacionMonto.getText().trim()));
                banco.guardarDatos();
                alerta("Mensaje", "Transferencia realizada con éxito", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnRegistrarCliente.setOnAction(e -> {
            try {
                validarRegistroCliente();
                Cliente c = new Cliente(
                        txtNombre.getText().trim(), txtApellido.getText().trim(),
                        txtId.getText().trim(), txtCorreo.getText().trim(),
                        txtTelefono.getText().trim(), txtPassword.getText().trim(),
                        txtNumeroCliente.getText().trim()
                );
                banco.registrarCliente(c);
                alerta("Mensaje", "Cliente registrado con éxito", Alert.AlertType.INFORMATION);
                limpiarRegistroCliente();
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnCrearCuenta.setOnAction(e -> {
            try {
                validarCrearCuenta();
                Cliente c = banco.buscarCliente(txtCuentaId.getText().trim());
                CuentaBancaria cuenta;

                if (cmbTipoCuenta.getValue().equals("Cuenta Corriente")) {
                    cuenta = new CuentaCorriente(
                            Integer.parseInt(txtCuentaNumero.getText().trim()),
                            c.getIdentificacion(),
                            Double.parseDouble(txtCuentaSaldo.getText().trim()),
                            500, 10
                    );
                } else {
                    cuenta = new CuentaAhorros(
                            Integer.parseInt(txtCuentaNumero.getText().trim()),
                            c.getIdentificacion(),
                            Double.parseDouble(txtCuentaSaldo.getText().trim()),
                            0.03
                    );
                }

                c.agregarCuenta(cuenta);
                banco.guardarDatos();
                alerta("Mensaje", "Cuenta creada exitosamente", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnBloquear.setOnAction(e -> {
            try {
                Cliente c = banco.buscarCliente(txtGestionId.getText().trim());
                CuentaBancaria cuenta = c.buscarCuenta(Integer.parseInt(txtGestionCuenta.getText().trim()));
                cuenta.setBloqueada(true);
                banco.guardarDatos();
                alerta("Mensaje", "Cuenta bloqueada", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnDesbloquear.setOnAction(e -> {
            try {
                Cliente c = banco.buscarCliente(txtGestionId.getText().trim());
                CuentaBancaria cuenta = c.buscarCuenta(Integer.parseInt(txtGestionCuenta.getText().trim()));
                cuenta.setBloqueada(false);
                banco.guardarDatos();
                alerta("Mensaje", "Cuenta desbloqueada", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });

        btnResumen.setOnAction(e -> {
            areaResumen.setText(
                    "Clientes: " + banco.getClientes().size()
                    + "\nEmpleados: " + banco.getEmpleados().size()
                    + "\nSaldo Banco: " + banco.calcularSaldoTotal()
            );
        });

        btnVerClientes.setOnAction(e -> {
            areaClientes.clear();
            for (Cliente c : banco.getClientes()) {
                areaClientes.appendText(c.getNombreCompleto() + " | " + c.getIdentificacion() + "\n");
            }
        });

        btnVerCuentas.setOnAction(e -> {
            areaCuentas.clear();
            for (Cliente c : banco.getClientes()) {
                for (CuentaBancaria cuenta : c.getCuentas()) {
                    areaCuentas.appendText(cuenta.getNumeroCuenta() + " | " + cuenta.getTipoCuenta() + " | " + cuenta.getSaldo() + "\n");
                }
            }
        });

        btnTodosMovimientos.setOnAction(e -> {
            try {
                areaMovimientos.clear();
                ArrayList<Movimiento> movs = Movimiento.cargarMovimientos();
                for (Movimiento m : movs) {
                    areaMovimientos.appendText(m.toString() + "\n");
                }
            } catch (Exception ex) {
                alerta("Error", ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    // METODOS AUXILIARES
   
    private void validarOperacion() throws Exception {
        if (txtOperacionId.getText().trim().isEmpty() || txtOperacionCuenta.getText().trim().isEmpty() || txtOperacionMonto.getText().trim().isEmpty()) {
            throw new Exception("Complete todos los campos de la operación");
        }
    }

    private void validarRegistroCliente() throws Exception {
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() || txtId.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()|| txtTelefono.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()|| txtNumeroCliente.getText().trim().isEmpty()) {
            throw new Exception("Complete todos los campos del registro");
        }
    }

    private void validarCrearCuenta() throws Exception {
        if (txtCuentaId.getText().trim().isEmpty()
                || txtCuentaNumero.getText().trim().isEmpty()
                || txtCuentaSaldo.getText().trim().isEmpty()) {
            throw new Exception("Complete todos los campos");
        }
    }

    private void limpiarRegistroCliente() {
        txtNombre.clear(); txtApellido.clear(); txtId.clear();
        txtCorreo.clear(); txtTelefono.clear(); txtPassword.clear();
        txtNumeroCliente.clear();
    }

    private void alerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}