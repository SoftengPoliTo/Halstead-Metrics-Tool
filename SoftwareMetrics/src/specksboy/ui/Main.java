package specksboy.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import specksboy.exporter.HTMLWriter;
import specksboy.exporter.PDFWriter;
import specksboy.halstead.metrics.Initiator;
import specksboy.halstead.metrics.MetricsEvaluator;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private FileSystemView fileSystemView;
	private File currentFile;
	@SuppressWarnings("unused")
	private JTree fileTree;
	private JButton exportHTML, exportPDF;
	private RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
	private JPanel panel = new JPanel();
	@SuppressWarnings("unused")
	private JPanel textPanel, drivePanel;
	@SuppressWarnings({ "unused", "rawtypes" })
	private JComboBox driveSelector;
	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	private JLabel Name, ProgramLength, ProgramVocabulary, EstimatedLength, PurityRatio, Volume, Difficulty,
			ProgramEffort, ProgrammingTime;
	private JTextField vName, vProgramLength, vProgramVocabulary, vEstimatedLength, vPurityRatio, vVolume, vDifficulty,
			vProgramEffort, vProgrammingTime;
	private JScrollPane treePane;

	public void setMetricsAndSource() {
		panel.setLayout(gridbag);
		c.gridx = 0;
		c.gridy = 0;
		Name = new JLabel("Name");
		panel.add(Name, c);
		c.gridx = 2;
		c.gridy = 0;
		vName = new JTextField();
		vName.setEditable(false);
		vName.setPreferredSize(new Dimension(150, 30));
		panel.add(vName, c);
		ProgramLength = new JLabel("Program Length");
		c.gridx = 0;
		c.gridy = 2;
		panel.add(ProgramLength, c);
		vProgramLength = new JTextField();
		vProgramLength.setEditable(false);
		c.gridx = 2;
		c.gridy = 2;
		vProgramLength.setPreferredSize(new Dimension(150, 30));
		panel.add(vProgramLength, c);
		ProgramVocabulary = new JLabel("Program Vocabulary");
		c.gridx = 0;
		c.gridy = 4;
		panel.add(ProgramVocabulary, c);
		vProgramVocabulary = new JTextField();
		c.gridx = 2;
		c.gridy = 4;
		vProgramVocabulary.setEditable(false);
		vProgramVocabulary.setPreferredSize(new Dimension(150, 30));
		panel.add(vProgramVocabulary, c);
		EstimatedLength = new JLabel("Estimated Length");
		c.gridx = 0;
		c.gridy = 6;
		panel.add(EstimatedLength, c);
		vEstimatedLength = new JTextField();
		c.gridx = 2;
		c.gridy = 6;
		vEstimatedLength.setPreferredSize(new Dimension(150, 30));
		vEstimatedLength.setEditable(false);
		panel.add(vEstimatedLength, c);
		PurityRatio = new JLabel("Purity Ratio");
		c.gridx = 0;
		c.gridy = 8;
		panel.add(PurityRatio, c);
		vPurityRatio = new JTextField();
		vPurityRatio.setEditable(false);
		vPurityRatio.setPreferredSize(new Dimension(150, 30));
		c.gridx = 2;
		c.gridy = 8;
		panel.add(vPurityRatio, c);
		Volume = new JLabel("Volume");
		c.gridx = 0;
		c.gridy = 10;
		panel.add(Volume, c);
		vVolume = new JTextField();
		vVolume.setPreferredSize(new Dimension(150, 30));
		vVolume.setEditable(false);
		c.gridx = 2;
		c.gridy = 10;
		panel.add(vVolume, c);
		Difficulty = new JLabel("Difficulty");
		c.gridx = 0;
		c.gridy = 12;
		panel.add(Difficulty, c);
		vDifficulty = new JTextField();
		vDifficulty.setPreferredSize(new Dimension(150, 30));
		vDifficulty.setEditable(false);
		c.gridx = 2;
		c.gridy = 12;
		panel.add(vDifficulty, c);
		ProgramEffort = new JLabel("Program Effort");
		c.gridx = 0;
		c.gridy = 14;
		panel.add(ProgramEffort, c);
		vProgramEffort = new JTextField();
		vProgramEffort.setPreferredSize(new Dimension(150, 30));
		vProgramEffort.setEditable(false);
		c.gridx = 2;
		c.gridy = 14;
		panel.add(vProgramEffort, c);
		ProgrammingTime = new JLabel("Programming Time");
		c.gridx = 0;
		c.gridy = 16;
		panel.add(ProgrammingTime, c);
		vProgrammingTime = new JTextField();
		vProgrammingTime.setPreferredSize(new Dimension(150, 30));
		vProgrammingTime.setEditable(false);
		c.gridx = 2;
		c.gridy = 16;
		panel.add(vProgrammingTime, c);
		exportHTML = new JButton("View HTML Report");
		Font font = exportHTML.getFont();
		exportHTML.setFont(new Font(font.getName(), Font.BOLD, font.getSize()));
		c.gridx = 0;
		c.gridy = 19;
		panel.add(exportHTML, c);
		exportHTML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				HTMLWriter obj = new HTMLWriter();
				try {
					obj.export(currentFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		exportPDF = new JButton("View PDF Report");
		Font pdffont = exportPDF.getFont();
		exportPDF.setFont(new Font(pdffont.getName(), Font.BOLD, pdffont.getSize()));
		c.gridx = 2;
		c.gridy = 19;
		panel.add(exportPDF, c);
		exportPDF.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				PDFWriter obj = new PDFWriter();
				try {
					obj.export(currentFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Metrics Value"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textPanel = new JPanel(new BorderLayout());
		textPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Source code"),
				BorderFactory.createEmptyBorder(0, 0, 0, 0)));
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(textArea);
		sp.setFoldIndicatorEnabled(true);
		JScrollPane textAreaScroller = new JScrollPane();
		textAreaScroller.getViewport().add(textArea);
		textPanel.add(textAreaScroller, BorderLayout.CENTER);

	}

	public void setTreeDetails() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode();
		treeModel = new DefaultTreeModel(root);

		TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tse.getPath().getLastPathComponent();
				showChildren(node);
				File file = (File) node.getUserObject();
				currentFile = file;
				try {
					textArea.setText(getFileContents(file));
					vName.setText(file.getName());
					setMetricsValue(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// show the file system roots.
		File[] roots = fileSystemView.getRoots();
		for (File fileSystemRoot : roots) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
			root.add(node);
			File[] files = fileSystemView.getFiles(fileSystemRoot, true);
			for (File file : files) {
				node.add(new DefaultMutableTreeNode(file));
			}
			//
		}

		tree = new JTree(treeModel);
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(treeSelectionListener);
		tree.setCellRenderer(new FileTreeCellRenderer());
		tree.expandRow(0);
	}

	private void showChildren(final DefaultMutableTreeNode node) {
		tree.setEnabled(false);

		SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
			@Override
			public Void doInBackground() {
				File file = (File) node.getUserObject();
				if (file.isDirectory()) {
					File[] files = fileSystemView.getFiles(file, true);
					if (node.isLeaf()) {
						for (File child : files) {
							publish(child);
						}
					}
				}
				return null;
			}

			@Override
			protected void process(List<File> chunks) {
				for (File child : chunks) {
					node.add(new DefaultMutableTreeNode(child));
				}
			}

			@Override
			protected void done() {
				tree.setEnabled(true);
			}
		};
		worker.execute();
	}

	public void setUpMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		menu.add(exit);
		menuBar.add(menu);

		setJMenuBar(menuBar);

	}

	public double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(val));
	}

	public Main() {
		super("Halstead Metrics");
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		fileSystemView = FileSystemView.getFileSystemView();
		setMetricsAndSource();
		setTreeDetails();
		setUpMenu();
		treePane = new JScrollPane(tree);
		tree.setVisibleRowCount(15);
		Dimension preferredSize = treePane.getPreferredSize();
		Dimension widePreferred = new Dimension(200, (int) preferredSize.getHeight());
		treePane.setPreferredSize(widePreferred);
		JSplitPane veriSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, new JScrollPane(panel),
				new JScrollPane(textPanel));

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(treePane),
				new JScrollPane(veriSplit));
		getContentPane().add(splitPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textArea.setEditable(false);
		setSize(800, 680);
		setVisible(true);
	}

	public void setMetricsValue(File file) {
		if (file != null && (file.getName().toLowerCase().contains(".java")
				|| file.getName().toLowerCase().contains(".c")
				|| file.getName().toLowerCase().endsWith(".cc")
				|| file.getName().toLowerCase().endsWith(".c++")
				|| file.getName().toLowerCase().contains(".cpp"))) {
			try {
				MetricsEvaluator me = new Initiator().initiate(file.getPath());
				vProgramLength.setText("" + RoundTo2Decimals(me.PROGRAM_LENGTH));
				vProgramEffort.setText("" + RoundTo2Decimals(me.PROGRAM_EFFORT));
				vProgrammingTime.setText("" + RoundTo2Decimals(me.PROGRAMMING_TIME));
				vProgramVocabulary.setText("" + RoundTo2Decimals(me.PROGRAM_VOCABULARY));
				vDifficulty.setText("" + RoundTo2Decimals(me.DIFFICULTY));
				vEstimatedLength.setText("" + RoundTo2Decimals(me.ESTIMATED_LENGTH));
				vPurityRatio.setText("" + RoundTo2Decimals(me.PURITY_RATIO));
				vVolume.setText("" + RoundTo2Decimals(me.VOLUME));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			vProgramLength.setText("");
			vProgramEffort.setText("");
			vProgrammingTime.setText("");
			vProgramVocabulary.setText("");
			vDifficulty.setText("");
			vEstimatedLength.setText("");
			vPurityRatio.setText("");
			vVolume.setText("");
		}

	}

	public String getFileContents(File file) throws Exception {
		if (file == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		if ((file.getName().contains(".java") || file.getName().contains(".cpp") || file.getName().contains(".c"))
				&& file.canRead()) {
			FileInputStream stream = new FileInputStream(file);
			Scanner fileScanner = new Scanner(stream);
			while (fileScanner.hasNext()) {
				buffer.append(fileScanner.nextLine() + "\n");
			}
			fileScanner.close();
			return buffer.toString();
		} else {
			return "";
		}
	}

	@SuppressWarnings("unused")
	private String getFileDetails(File file) {
		if (file == null)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append("Name: " + file.getName() + "\n");
		buffer.append("Path: " + file.getPath() + "\n");
		buffer.append("Size: " + file.length() + "\n");
		return buffer.toString();
	}

	public static void main(String args[]) {
		new Main();
	}
}

@SuppressWarnings("serial")
class FileTreeCellRenderer extends DefaultTreeCellRenderer {

	private FileSystemView fileSystemView;

	private JLabel label;

	FileTreeCellRenderer() {
		label = new JLabel();
		label.setOpaque(true);
		fileSystemView = FileSystemView.getFileSystemView();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		File file = (File) node.getUserObject();
		label.setIcon(fileSystemView.getSystemIcon(file));
		label.setText(fileSystemView.getSystemDisplayName(file));
		label.setToolTipText(file.getPath());

		if (selected) {
			label.setBackground(backgroundSelectionColor);
			label.setForeground(textSelectionColor);
		} else {
			label.setBackground(backgroundNonSelectionColor);
			label.setForeground(textNonSelectionColor);
		}

		return label;
	}
}