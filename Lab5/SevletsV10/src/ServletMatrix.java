import java.io.*;
import java.util.Arrays;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletMatrix extends HttpServlet{
	
	private String getMatrixHTML(int[][] matrix) {
		String body = "";
		int n = matrix.length;
		for(int i=0; i<n; i++) {
        	String row = Arrays.toString(matrix[i]);
        	body += "<p>"+row.substring(1, row.length()-1).replace(",", "")+"</p>";
        	body += "\n";
    	}
		return body;
	}
	
	private int[][] getTranspose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int transpose[][] = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }
	
	private int[][] calcCoFactors(int matrix[][], int r, int c, int rows) {
        int coFactors[][] = new int[rows - 1][rows - 1];
        int row = 0, col = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (i != r && j != c) {
                    coFactors[row][col] = matrix[i][j];
                    col++;
                    if (col == rows - 1) {
                        col = 0;
                        row++;
                    }
                }
            }
        }

        return coFactors;
    }

    private int calcDeterminant(int matrix[][], int rows) {
        int det = 0;

        if (rows == 1)
            return matrix[0][0];

        int sign = 1;
        for (int i = 0; i < rows; i++) {
            int coFactors[][] = calcCoFactors(matrix, 0, i, rows);
            det += sign * matrix[0][i] * calcDeterminant(coFactors, rows - 1);
            sign *= -1;
        }
        return det;
    }
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    	// Process the matrix
    	String matrixData = request.getParameter("MatrixData");
    	String[] rows = matrixData.split("\\r?\\n");
    	
    	int n = rows.length;
    	int m = rows[0].split(" ").length;
    	int[][] matrix = new int[n][m];
    	for(int i=0; i<n; i++) {
    		String[] cols = rows[i].split(" ");
    		if(cols.length > 0) {
    			for(int j=0; j<m; j++) {
        			matrix[i][j] = Integer.parseInt(cols[j]);
        		}
    		}
    	}
        String body = "<div id=\"OriginalMatrix\">"+getMatrixHTML(matrix)+"</div>";
        
        // Process the user choice
        String transposeValue = request.getParameter("transpose");
        boolean doTranspose = transposeValue == null ? false : true;
        
        String determinantValue = request.getParameter("determinant");
        boolean doDeterminant = determinantValue == null ? false : true;
        
        if(doDeterminant) {
        	body += "<h2>Determinante</h2>";
        	if (matrix.length != matrix[0].length) {
        		body += "<p>The Determinant of a Matrix is a real number that can be defined for square matrices only</p>";
            } else {
                int det = calcDeterminant(matrix, matrix.length);
                body += "<p>"+Integer.toString(det)+"</p>";
            }
        }
        
        if(doTranspose) {
        	body += "<h2>Transpose</h2><div id=\"MatrixTranspose\">";
        	int[][] transpose = getTranspose(matrix);
        	body += getMatrixHTML(transpose);
        	body += "</div>";
        }
        
        response.setContentType("text/html");
        String page = "<!doctype html> <html> <body bgcolor=\"#f0f0f0\" align=\"center\"><h1>Result</h1><h2>Original Matrix</h2>"+body+"</body></html>";
        response.getWriter().println(page);
    }

}
