package Calculator;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

    /**
     * 计算器类
     *
     * @author 小明
     *
     */
    public class Calculator extends JFrame implements ActionListener {

        private static final long serialVersionUID = 2499964079704437558L;

        private JTextField result; // 显示运算结果的文本框
        private JButton[] buttons; // 所有的按钮对象
        private final String[] characters = { "7", "8", "9", "/", "4", "5", "6",
                "*", "1", "2", "3", "-", "0", ".", "=", "+" }; // 所有的按钮文本
        private boolean isFirstDigit = true; // 标记第一次输入数字
        private String operator = "="; // 运算符
        private double resultNum = 0.0; // 运算结果

        public Calculator(String title) {
            // 标题栏
            super(title);
            // 大小
            setSize(220, 200);
            // 居中
            setLocationRelativeTo(null);
            // 默认关闭操作
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            // 禁止修改大小
            setResizable(false);
            // 初始化文本框与按钮
            generateInterface();
            // 显示
            setVisible(true);
        }

        /**
         * 初始化文本框与按钮，生成界面
         */
        private void generateInterface() {
            /* 文本框 */
            result = new JTextField("0");
            // 右对齐
            result.setHorizontalAlignment(JTextField.RIGHT);
            // 不允许编辑
            result.setEditable(false);
            // 将文本框添加到窗体北方
            add(result, BorderLayout.NORTH);

            /* 按钮 */
            buttons = new JButton[characters.length];
            JPanel pnl = new JPanel(new GridLayout(4, 4, 5, 5));
            for (int i = 0; i < buttons.length; i++) {
                buttons[i] = new JButton(characters[i]);
                buttons[i].addActionListener(this);
                buttons[i].setFocusable(false); // 不允许按钮定位焦点
                pnl.add(buttons[i]);
            }
            // 将所有按钮添加到窗体的中间
            add(pnl, BorderLayout.CENTER);

            // 允许内容面板定位焦点
            this.getContentPane().setFocusable(true);

            // 注册内容面板事件监听器
            // 使用适配器实现
            this.getContentPane().addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    char ch = e.getKeyChar(); // 获取按钮字符
                    /* 处理数字或运算符 */
                    if ('.' == ch || Character.isDigit(ch)) { // 数字小数点
                        handleNumber(String.valueOf(ch));
                    } else if ("+-*/=".indexOf(ch) != -1 || e.getKeyCode() == 10) { // 运算符
                        handleOperator(String.valueOf(ch));
                    } else if (e.getKeyCode() == 8) { // 退格键
                        String tmp = result.getText();
                        if (tmp.length() == 1) {
                            result.setText("0");
                            isFirstDigit = true;
                        } else {
                            result.setText(tmp.substring(0, tmp.length() - 1));
                        }
                    }
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取点击按钮的文本
            String text = e.getActionCommand();

            /* 处理数字或运算符 */
            if (".".equals(text) || Character.isDigit(text.charAt(0))) { // 数字小数点
                handleNumber(text);
            } else if ("+-*/=".indexOf(text) != -1) { // 运算符
                handleOperator(text);
            }
        }

        /**
         * 处理数字与小数点
         *
         * @param text
         */
        private void handleNumber(String text) {
            if (isFirstDigit) { // 第一次输入
                if (".".equals(text)) {
                    this.result.setText("0.");
                } else {
                    this.result.setText(text);
                }
            } else if ("0".equals(text) && "0".equals(this.result.getText())) { // 输入0
                isFirstDigit = true;
                return;
            } else if (".".equals(text) && this.result.getText().indexOf(".") == -1) { // 输入小数点
                this.result.setText(this.result.getText() + ".");
            } else if (!".".equals(text)) { // 输入不为小数点
                this.result.setText(this.result.getText() + text);
            }
            // 修改第一次输入标记
            isFirstDigit = false;
        }

        /**
         * 处理运算符
         *
         * @param text
         */
        private void handleOperator(String text) {
            /* 进行算术运算判断 */
            switch (operator) {
                case "+":
                    resultNum += Double.parseDouble(this.result.getText());
                    break;
                case "-":
                    resultNum -= Double.parseDouble(this.result.getText());
                    break;
                case "*":
                    resultNum *= Double.parseDouble(this.result.getText());
                    break;
                case "/":
                    resultNum /= Double.parseDouble(this.result.getText());
                    break;
                case "=":
                    resultNum = Double.parseDouble(this.result.getText());
                    break;
            }
            // 将运算结果显示到文本框中
            this.result.setText(String.valueOf(resultNum));
            // 将参数运算符放入成员变量中
            this.operator = text;
            // 下一个数字是第一次输入
            isFirstDigit = true;
        }
    }
}
