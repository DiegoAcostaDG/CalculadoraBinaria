package Calculadora;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Calculadora extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JLabel operationLabel;  // Nueva etiqueta para mostrar las operaciones
    private String input = "";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
                Calculadora frame = new Calculadora();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Calculadora() {
        setTitle("Conversor de Sistemas Numéricos");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 350, 500);  // Aumentamos el tamaño para incluir la etiqueta
        setLayout(new BorderLayout());

        // Campo de texto para los números
        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);

        // Nueva etiqueta para mostrar las operaciones
        operationLabel = new JLabel("Operaciones: ", SwingConstants.RIGHT);
        operationLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Aumentamos el tamaño de la fuente
        operationLabel.setForeground(Color.BLACK);  // Texto en color negro para mayor contraste
        operationLabel.setBackground(new Color(255, 255, 204));  // Fondo suave
        operationLabel.setOpaque(true);  // Hacemos que el fondo sea visible
        operationLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));  // Borde azul para resaltar
        add(operationLabel, BorderLayout.SOUTH);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 filas, 4 columnas
        buttonPanel.setBackground(new Color(220, 220, 220));
        add(buttonPanel, BorderLayout.CENTER);

        // Botones de la calculadora con nombres claros y sin operaciones matemáticas
        String[] buttons = {
            "7", "8", "9",
            "4", "5", "6",
            "1", "2", "3",
            "0", ".", "Decimal",
            "Binario", "Octal", "Hexadecimal",
            "Limpiar", "Salir"
        };

        // Crear los botones en el orden deseado
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(new ButtonClickListener());

            // Diseño visual de los botones
            button.setBackground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            button.setPreferredSize(new Dimension(80, 50));

            if ("Limpiar".equals(text) || "Salir".equals(text)) {
                button.setBackground(new Color(255, 153, 153)); // Color para limpiar y salir
            } else if ("Decimal".equals(text) || "Binario".equals(text) || "Octal".equals(text) || "Hexadecimal".equals(text)) {
                button.setBackground(new Color(204, 204, 255)); // Color para los sistemas
            }

            buttonPanel.add(button);
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                if (command.matches("\\d|\\.")) {
                    input += command;
                    textField.setText(input);
                } else if ("Limpiar".equals(command)) {
                    input = "";
                    textField.setText("");
                    operationLabel.setText("Operaciones: ");
                } else if ("Salir".equals(command)) {
                    System.exit(0);
                } else if ("Decimal".equals(command)) {
                    textField.setText(input);
                    operationLabel.setText("Operaciones: " + input + " (Decimal)");
                } else if ("Binario".equals(command)) {
                    if (!input.isEmpty()) {
                        int num = Integer.parseInt(input);
                        String result = Integer.toBinaryString(num);
                        String operationDetail = getBinaryOperationDetail(num);
                        textField.setText(result);
                        operationLabel.setText("Operaciones: " + operationDetail);
                    }
                } else if ("Octal".equals(command)) {
                    if (!input.isEmpty()) {
                        int num = Integer.parseInt(input);
                        String result = Integer.toOctalString(num);
                        String operationDetail = getOctalOperationDetail(num);
                        textField.setText(result);
                        operationLabel.setText("Operaciones: " + operationDetail);
                    }
                } else if ("Hexadecimal".equals(command)) {
                    if (!input.isEmpty()) {
                        int num = Integer.parseInt(input);
                        String result = Integer.toHexString(num).toUpperCase();
                        String operationDetail = getHexadecimalOperationDetail(num);
                        textField.setText(result);
                        operationLabel.setText("Operaciones: " + operationDetail);
                    }
                }
            } catch (NumberFormatException ex) {
                textField.setText("Error");
                operationLabel.setText("Operaciones: Error");
            }
        }

        // Método para obtener la descomposición binaria
        private String getBinaryOperationDetail(int num) {
            StringBuilder details = new StringBuilder(num + " = ");
            int power = 0;
            while (num > 0) {
                int bit = num % 2;
                details.append("(").append(bit).append("×2^").append(power).append(") ");
                num /= 2;
                power++;
            }
            return details.toString();
        }

        // Método para obtener la descomposición octal
        private String getOctalOperationDetail(int num) {
            StringBuilder details = new StringBuilder(num + " = ");
            int power = 0;
            while (num > 0) {
                int digit = num % 8;
                details.append("(").append(digit).append("×8^").append(power).append(") ");
                num /= 8;
                power++;
            }
            return details.toString();
        }

        // Método para obtener la descomposición hexadecimal
        private String getHexadecimalOperationDetail(int num) {
            StringBuilder details = new StringBuilder(num + " = ");
            int power = 0;
            while (num > 0) {
                int digit = num % 16;
                details.append("(").append(Integer.toHexString(digit).toUpperCase()).append("×16^").append(power).append(") ");
                num /= 16;
                power++;
            }
            return details.toString();
        }
    }
}
