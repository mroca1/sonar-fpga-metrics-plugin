 /*
 * sonar-fpga-metrics plugin for Sonarqube
 * Copyright (C) 2020 Linty Services
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.linty.plugins.fpgametrics.measures;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;
import org.sonar.api.scanner.sensor.ProjectSensor;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;


/**
 * Scanner feeds raw measures on files but must not aggregate values to directories and project.
 * This class emulates loading of file measures from a 3rd-party analyser.
 */

public class SetSizeOnFilesSensor implements ProjectSensor {


	@Override
	public void describe(SensorDescriptor descriptor) {
		descriptor.name("Compute size of file names");
	}

	@Override
	public void execute(SensorContext context) {

	

		Gson gson = new Gson();
		Map<String,Object> measures = new HashMap<String,Object>();
		try {
			measures = gson.fromJson(new FileReader("measures.json"), measures.getClass());
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

			for(Metric metric:ImportedMetrics.getMetricsResult) {
				
				 FileSystem fs = context.fileSystem();
				    // only "main" files, but not "tests"
				    Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
				    for (InputFile file : files) {
				    	Gson gson_file = new Gson();
						Map<String,Object> measures_file = new HashMap<String,Object>();
						try {
							measures_file = gson_file.fromJson(new FileReader(FilenameUtils.removeExtension(file.absolutePath())+"_measures.json"), measures_file.getClass());
						} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
					  Object rawValue_file = measures_file.get(metric.getKey());
					  if (rawValue_file!=null) {
						  String valueTypeStr = metric.getType().name();//ExampleMetrics.measures.metrics().get(metric.getKey()).getType();
							switch (valueTypeStr) {
							case "INT":
								context.newMeasure().forMetric(metric).on(file).withValue((int)Math.round((Double) rawValue_file)).save();
								break;
							case "FLOAT":
								context.newMeasure().forMetric(metric).on(file).withValue((Double) rawValue_file).save();
								break;
							case "PERCENT":
								context.newMeasure().forMetric(metric).on(file).withValue((Double) rawValue_file).save();
								break;
							case "BOOL":
								context.newMeasure().forMetric(metric).on(file).withValue((Boolean) rawValue_file).save();
								break;
							case "STRING":
								context.newMeasure().forMetric(metric).on(file).withValue((String) rawValue_file).save();
								break;
							case "MILLISEC":
								context.newMeasure().forMetric(metric).on(file).withValue((Long)Math.round((Double) rawValue_file)).save();
								break;
							case "DATA":
								context.newMeasure().forMetric(metric).on(file).withValue((String) rawValue_file).save();
								break;
							case "LEVEL":
								//context.addMeasure((String)me.getKey(), (Metric.Level.class) ((JsonMetric)me.getValue()).getValue());
								break;
							case "DISTRIB":
								context.newMeasure().forMetric(metric).on(file).withValue((String) rawValue_file).save();
								break;
							case "RATING":
								context.newMeasure().forMetric(metric).on(file).withValue((int)Math.round((Double) rawValue_file)).save();
								break;
							case "WORK_DUR":
								context.newMeasure().forMetric(metric).on(file).withValue((Long)Math.round((Double) rawValue_file)).save();
								break;
							default:
								;
							}
					  }
				    }
				
				Object rawValue = measures.get(metric.getKey());
				if (rawValue!=null) {
					String valueTypeStr = metric.getType().name();//ExampleMetrics.measures.metrics().get(metric.getKey()).getType();
					switch (valueTypeStr) {
					case "INT":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((int)Math.round((Double) rawValue)).save();
						break;
					case "FLOAT":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((Double) rawValue).save();
						break;
					case "PERCENT":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((Double) rawValue).save();
						break;
					case "BOOL":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((Boolean) rawValue).save();
						break;
					case "STRING":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((String) rawValue).save();
						break;
					case "MILLISEC":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((Long)Math.round((Double) rawValue)).save();
						break;
					case "DATA":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((String) rawValue).save();
						break;
					case "LEVEL":
						//context.addMeasure((String)me.getKey(), (Metric.Level.class) ((JsonMetric)me.getValue()).getValue());
						break;
					case "DISTRIB":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((String) rawValue).save();
						break;
					case "RATING":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((int)Math.round((Double) rawValue)).save();
						break;
					case "WORK_DUR":
						context.newMeasure().forMetric(metric).on(context.project()).withValue((Long)Math.round((Double) rawValue)).save();
						break;
					default:
						;
					}
				}
			}
		

	}
}
