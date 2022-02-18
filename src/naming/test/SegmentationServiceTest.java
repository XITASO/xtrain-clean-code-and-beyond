import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SegmentationServiceTest {

	private String[] testData = new String[]
			{"id,first,second,third,fourth",
			 "1,1,2,3,4",
			 "2,5,6,7,8",
			 "3,9,10,11,12",
			 "4,13,14,15,16",
			 "5,17,18,19,20"};

	@Test
	public void getSegmentDataReturnsRequestedLines() throws Exception {
		SegmentationService service = new SegmentationService();

		int fileId = 1;
		createFileWithTestData(fileId);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(service.GetSegmentData(fileId, 2, 4)))) {
			List<String> lines = reader.lines().collect(Collectors.toList());

			assertThat(lines.size(), is(3));
			assertThat(lines.get(0), is(testData[0])); // header
			assertThat(lines.get(1), is(testData[3]));
			assertThat(lines.get(2), is(testData[4]));
		};
	}

	@Test
	public void getSegmentDataLimitsResultsWhenExpectedLinesGiven() throws Exception {
		SegmentationService service = new SegmentationService();

		int fileId = 1;
		createFileWithTestData(fileId);

		int expectedLines = 2;

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(service.GetSegmentData(fileId, 0, 5, expectedLines)))) {
			List<String> lines = reader.lines().collect(Collectors.toList());

			assertThat(lines.size(), is(expectedLines + 1));
			assertThat(lines.get(0), is(testData[0])); // header
			assertThat(lines.get(1), is(testData[2]));
			assertThat(lines.get(2), is(testData[4]));
		};
	}

	private void createFileWithTestData(int fileId) throws IOException, URISyntaxException {
		String basePath = new File(
				SegmentationService.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
						.getParent();
		var filePath = Paths.get(basePath, fileId + ".csv").toString();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for(String line : testData) {
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		}
	}

}
