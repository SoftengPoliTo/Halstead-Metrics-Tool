package cli;

import specksboy.halstead.metrics.Initiator;
import specksboy.halstead.metrics.MetricsEvaluator;
import specksboy.halstead.metrics.Operands;
import specksboy.halstead.metrics.Operators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;

import org.json.simple.*;

public class NewMain {
	public static double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		return Double.valueOf(df2.format(val));
	}
	public static void main(String[] args) {
		if(args.length==0)
			specksboy.ui.Main.main(null);
		
		else if(args[0].equals("-h") || args[0].equals("--help") || args[0].equals("-help")  ) {
			System.out.print("Usage:\n\tTo start GUI: HalsteadTool\n\tTo use in CLI mode: HalsteadTool <path name>\n");
			System.out.print("\tIn CLI mode the results will be printed to stdout in Json format.\n");
			System.exit(1);
		}
		else {
			File file = new File(args[0]);
			if(file==null || !file.isFile() || !file.canRead()) {
				System.out.print("Cannot read file: " + file.getAbsolutePath() + "\n");
				System.exit(2);
			}
			
			if (file != null && (file.getName().toLowerCase().endsWith(".java")
					|| file.getName().toLowerCase().endsWith(".c")
					|| file.getName().toLowerCase().endsWith(".cc")
					|| file.getName().toLowerCase().endsWith(".cpp") )) {
				try {
					
					MetricsEvaluator me = new Initiator().initiate(file.getPath());
					/*
					System.out.print("Halstead Metrics Tool - Cli -\n");
					System.out.print("vProgramLength : " + me.PROGRAM_LENGTH + "\n");
					System.out.print("vProgramEffort : " + me.PROGRAM_EFFORT + "\n");
					System.out.print("vProgrammingTime : " + me.PROGRAMMING_TIME + "\n");
					System.out.print("vProgramVocabulary : " + me.PROGRAM_VOCABULARY + "\n");
					System.out.print("vDifficulty : " + me.DIFFICULTY + "\n");
					System.out.print("vEstimatedLength : " + me.ESTIMATED_LENGTH + "\n");
					System.out.print("vPurityRatio : " + me.PURITY_RATIO + "\n");
					System.out.print("vVolume : " + me.VOLUME + "\n");
					*/
					JSONObject j = new JSONObject();
					
					JSONObject h_json = new JSONObject();
					h_json.put("n1", (int) me.n1);
					h_json.put("n2", (int) me.n2);
					h_json.put("N1", (int) me.N1);
					h_json.put("N2", (int) me.N2);
					
					h_json.put("Vocabulary", RoundTo2Decimals(me.PROGRAM_VOCABULARY));
					h_json.put("Length", RoundTo2Decimals(me.PROGRAM_LENGTH));
					h_json.put("Estimated program length", RoundTo2Decimals(me.ESTIMATED_LENGTH));
					h_json.put("Volume", RoundTo2Decimals(me.VOLUME));
					h_json.put("Difficlty", RoundTo2Decimals(me.DIFFICULTY));
					h_json.put("Effort", RoundTo2Decimals(me.PROGRAM_EFFORT));
					
					h_json.put("Programming time", me.PROGRAMMING_TIME);
					h_json.put("Purity ratio", RoundTo2Decimals(me.PURITY_RATIO) );
					
					
					JSONObject oprtrs = new JSONObject();
					for (int i = 0; i < Operators.getInstance().name.size(); i++)
						oprtrs.put( Operators.getInstance().name.get(i).toString(),
									Operators.getInstance().count.get(i).toString() );
					
					JSONObject oprnds = new JSONObject();
					for (int i = 0; i < Operands.getInstance().name.size(); i++)
							oprnds.put( Operands.getInstance().name.get(i).toString(),
										Operands.getInstance().count.get(i).toString() );
					
					h_json.put("_Operands", oprnds );
					h_json.put("_Operators", oprtrs );
					
					j.put("Halstead", h_json);
					j.put("Filename", file.getName() );
					
/*					//WRITE to file
					BufferedWriter writer = new BufferedWriter(new FileWriter( "output_halstead_tool__"+ file.getName() + ".json" ));
					writer.write(j.toJSONString());
					writer.close();
					System.out.print("Output written in file: output_halstead_tool__"+ file.getName() + ".json\n");
*/					
					//WRITE to stdout
					System.out.print(j.toJSONString());
					
				} catch (Exception e) {
					System.err.print("MetricsEvaluator exception.\n");
					e.printStackTrace();
					System.exit(4);
				}
				
			}
			else {
				System.out.print("Check the path you inserted: " + file.getAbsolutePath() + "\n");
				System.exit(3);
			}
		}
	}

}
