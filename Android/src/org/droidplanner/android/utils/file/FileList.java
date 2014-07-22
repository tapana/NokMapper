package org.droidplanner.android.utils.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

public class FileList {

	static public String[] getWaypointFileList() {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.contains(".txt");
			}
		};
		return getFileList(DirectoryPath.getWaypointsPath(), filter);
	}

	public static String[] getParametersFileList() {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.contains(".param");
			}
		};
		return getFileList(DirectoryPath.getParametersPath(), filter);
	}

	public static String[] getCameraInfoFileList() {
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return filename.contains(".xml");
			}
		};
		return getFileList(DirectoryPath.getCameraInfoPath(), filter);
	}

	static public String[] getFileList(String path, FilenameFilter filter) {
		File mPath = new File(path);
		try {
			mPath.mkdirs();			
			if (mPath.exists()) {
				File[] files = mPath.listFiles(filter);
				if(files == null)return new String[0];	
				
				if ( files.length > 1) {
					//sort by name
					Arrays.sort(files);
					
					//sort by last modified
					/*Arrays.sort(files, new Comparator<File>() {
			             @Override
			             public int compare(File f1, File f2) {
			                return (f1.lastModified() < f2.lastModified()) ?1:-1;
			                
			             }
			        });*/
				}
				
				String[] nameList = new String[files.length];
				for(int i=0;i<files.length;i++){
					nameList[i] = files[i].getName();
				}				
				return nameList;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

}
