import java.io.FileWriter;
import java.io.IOException;
import java.security.Policy.Parameters;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

	
public class Report {

	static List<Parameter> parameters;

	public Report(){

		parameters = new ArrayList<Parameter>();
	}

	public void function(List<Service> service) {
		generateChart(service);
		writeToFile(service);
	}


	public void addToList(Parameter parameter) {
		parameters.add(parameter);
	}

	public static String generateChart(List<Service> service) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Service p : service) {
			dataset.setValue(p.getParameters().getUsageCostDaily(), p.getParameters().getServiceName(), "");
		}

		JFreeChart barChart = ChartFactory.createBarChart(
				"Grafico a Barre ", // Titolo del grafico
				"Categorie", // Etichetta asse x
				"Valori", // Etichetta asse y
				dataset, // Dati per il grafico
				PlotOrientation.VERTICAL, // Orientamento del grafico
				true, // Includi legenda
				true, // Mostra tooltips
				false // Mostra URL

				);

		ChartFrame frame = new ChartFrame("Grafico a Barre", barChart);
		frame.pack();
		frame.setVisible(true);

		return barChart.toString(); // Restituisci la rappresentazione testuale del grafico
	}



	public static void writeToFile(List<Service> services) {
		try {
			// Crea un oggetto FileWriter per scrivere nel file di testo
			FileWriter writer = new FileWriter("file.txt");

			// Scrivi i dati nel file
			writer.write("Service: ");


			services.forEach(s-> {s.getParameters().getBrokenDevice().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numTimeBrokenDevice: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getDeviceOn().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numTimegetDeviceOn: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getDeviceOff().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numTimegetDeviceOff: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getNumEnergySaving().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numNumEnergySaving: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});


			services.forEach(s-> {s.getParameters().getNumMaxLuminosity().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numMaxLuminosity: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getNumMinLuminosity().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numMinLuminosity: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getNumMinTemp().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numMinTemp: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			});

			services.forEach(s-> {s.getParameters().getNumMaxTemp().forEach((key, value) -> {
				String data1 =s.serviceName;

				System.out.println(data1 +" "+ key + " "+value);
				try {


					writer.write("Service: ");
					writer.write(data1);
					writer.write(",  Device: ");
					writer.write(key.toString());
					writer.write(" numMaxTemp: ");
					writer.write(value.toString());

					writer.write("\n\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			});

			// Chiudi il writer
			writer.close();
			System.out.println("File di testo creato correttamente.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

	



