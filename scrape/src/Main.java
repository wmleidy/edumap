import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
//import org.apache.pdfbox.searchengine.lucene.LucenePDFDocument;


public class Main {

	public static void main(String[] args) throws Exception{
		PDFTextStripper stripper = new PDFTextStripper();
		//System.out.println(stripper);
		File file = new File("iste.pdf");
		PDDocument doc = PDDocument.load(file);
		//System.out.println(doc);
		/*stripper.setStartPage(30);
		stripper.setEndPage(30);*/
		//stripper.writeText(doc, System.out);
		String text = stripper.getText(doc);
		//System.out.println(text);

		/*
		 * Place stripped lines in List
		 */

		List<String> standards = new ArrayList<String>();
		int size = text.length();
		Boolean inLine = true;
		String line = "";
		for(int c = 0; c < size; c++){
			char current = text.charAt(c);
			//String pair = current + text.charAt(c+1);
			if((current == '.') && inLine){
				standards.add(line);
				//System.out.println(line);
				line = "";
				//inLine = false;
			}
			else if(inLine){
				if(current == '\n' || current == '\r'){
					line += "";
				}
				else{
					line += current;
				}
			}
		}

		/*
		 * Write to output files
		 */

		FileWriter fw = new FileWriter("output.csv", false);
		BufferedWriter bw = new BufferedWriter(fw);

		System.out.println("Started.");

		/*for(String standard : standards){
			//System.out.println(standard.substring(0, 2));
			if(standard.substring(0, 2).equals("L.")){
				String[] pair = splitByFirst(' ', standard);
				bw.write(pair[0] + "," + pair[1]);
			}
		}*/


		for(String standard : standards){
			System.out.println(standard);
		}

		bw.close();
		System.out.println("Finished.");

	}

	public static String[] splitByFirst(char splitter, String line){
		String[] response = {"", "\""};

		char[] charArray = line.toCharArray();
		Boolean code = true;
		for(char c : charArray){
			if(code){
				if(c == splitter){
					code = false;
				}
				else{
					response[0] += c;
				}
			}
			else{
				if(c == '\r'){
					response[1] += "\"";
				}
				response[1] += c;
			}
		}
		return response;
	}

}
