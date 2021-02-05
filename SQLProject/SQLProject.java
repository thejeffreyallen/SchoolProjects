import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.ResultSet;

/**
 * Java class to connect to and manipulate an SQL database located on the Onyx server 
 * 
 * CS-HU310 Spring 2020
 * 
 * @author Jeff Allen
 */
class Project {

	private static Connection conn = null;
	private static String database = "msandbox";
	private static String password = "";

	/**
	 * Attempt to create a connection with our MySQL database
	 */
	private static void makeConnection() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:57847/test?verifyServerCertificate=false&useSSL=true", database, password);
			System.out.println("Connection to " + database + " successful!");
			System.out.println();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * Make sure program is using the correct database
	 * 
	 * @param name - name of database to use
	 */
	private static void useDatabase(String name) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("USE " + name + ";");

			pstmt.executeUpdate();
			System.out.println("Using " + name + "...");

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Create a new item an add it to the Item table
	 * 
	 * @param itemCode        - NOT NULL
	 * @param itemDescription
	 * @param price
	 */
	private static void CreateItem(String itemCode, String itemDescription, String price) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Call CreateItem('" + itemCode + "', '" + itemDescription + "'," + price + ");");

			pstmt.executeUpdate();
			System.out.println("Item " + itemCode + " created.\n");

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Create a new purchase and add it to the Purchase table
	 * 
	 * @param itemCode
	 * @param quantity
	 */
	private static void CreatePurchase(String itemCode, int quantity) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Call CreatePurchase('" + itemCode + "', " + quantity + ");");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Purchase of " + quantity + " " + itemCode + " created.\n");
			} else {
				System.out.println("Unable to create purchase of " + itemCode
						+ " because it was not found in the item list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Create a new shipment and add it to the Shipment table
	 * 
	 * @param itemCode     - must be a valid item code
	 * @param quantity     - number of items to ship
	 * @param shipmentDate - Date of shipment
	 */
	private static void CreateShipment(String itemCode, int quantity, Date shipmentDate) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(
					"Call CreateShipment('" + itemCode + "', " + quantity + ", '" + shipmentDate + "');");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Shipment of " + quantity + " " + itemCode + " created.\n");
			} else {
				System.out.println("Unable to create shipment of " + itemCode
						+ " because it was not found in the item list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Gets item table from the database
	 * 
	 * @param itemCode - type of item to retrieve from the table. '%' for all
	 */
	private static void GetItems(String itemCode) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Call GetItems('" + itemCode + "');");

			System.out.println("ID|ItemCode|ItemDescription|Price\n");

			rs.beforeFirst();

			while (rs.next()) {
				System.out
						.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getDouble(4));
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Gets Purchases table from the database
	 * 
	 * @param itemCode - type of item to retrieve from the table. '%' for all
	 */
	private static void GetPurchases(String itemCode) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Call GetPurchases('" + itemCode + "');");

			System.out.println("ID|ItemID|Quantity|PurchaseDate\n");

			rs.beforeFirst();

			while (rs.next()) {
				System.out.println(rs.getInt(1) + " | " + rs.getInt(2) + " | " + rs.getInt(3) + " | " + rs.getDate(4));
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Gets Shipment table from the database
	 * 
	 * @param itemCode - type of item to retrieve from the table. '%' for all
	 */
	private static void GetShipments(String itemCode) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Call GetShipments('" + itemCode + "');");

			System.out.println("ID|ItemID|Quantity|ShipmentDate\n");

			rs.beforeFirst();

			while (rs.next()) {
				System.out.println(rs.getInt(1) + " | " + rs.getInt(2) + " | " + rs.getInt(3) + " | " + rs.getDate(4));
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Returns all available items
	 * 
	 * @param itemCode - type of item to retrieve from the table. '%' for all
	 */
	private static void ItemsAvailable(String itemCode) {
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Call ItemsAvailable('" + itemCode + "');");

			System.out.println("ItemCode|ItemDescription|ItemsAvailable\n");

			rs.beforeFirst();

			while (rs.next()) {
				System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getInt(3));
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqlEx) {
				} // ignore
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				stmt = null;
			}
		}
	}

	/**
	 * Change the passed in item parameter's price
	 * 
	 * @param itemCode - type of item to update
	 * @param price    - new price to override the previous
	 */
	private static void UpdateItem(String itemCode, String price) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Call UpdateItem('" + itemCode + "', " + price + ");");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Updated " + itemCode + "'s price to " + price + ".\n");
			} else {
				System.out.println(
						"Unable to update " + itemCode + " because it was not found in the item list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Delete the passed in item from the item table
	 * 
	 * @param itemCode - item to be deleted
	 */
	private static void DeleteItem(String itemCode) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Call DeleteItem('" + itemCode + "');");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Deleted " + itemCode + " from the item list.");
			} else {
				System.out.println(
						"Unable to delete " + itemCode + " because it was not found in the item list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Deletes the purchase from the purchase table
	 * 
	 * @param itemCode - item to be deleted
	 */
	private static void DeletePurchase(String itemCode) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Call DeletePurchase('" + itemCode + "');");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Deleted " + itemCode + " from the purchase list.");
			} else {
				System.out.println(
						"Unable to delete " + itemCode + " because it could not be found in the purchase list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Deletes the shipment from the shipment table
	 * 
	 * @param itemCode - item to be deleted
	 */
	private static void DeleteShipment(String itemCode) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Call DeleteShipment('" + itemCode + "');");

			if (pstmt.executeUpdate() >= 1) {
				System.out.println("Deleted " + itemCode + " from the shipment list.");
			} else {
				System.out.println(
						"Unable to delete " + itemCode + " because it could not be found in the shipment list.");
			}

		} catch (SQLException ex) {
			// handle any errors
			System.err.println("SQLException: " + ex.getMessage());
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException sqlEx) {
				} // ignore
				pstmt = null;
			}
		}
	}

	/**
	 * Program usage instructions
	 */
	private static void printUsage() {
		System.err.println("PROGRAM USAGE \n/?: Print usage instructions.");
		System.err.println("CreateItem <itemCode> <itemDescription> <price> - creates item record.");
		System.err.println("CreatePurchase <itemCode> <purchaseQuantity> - creates purchase record.");
		System.err.println("CreateShipment <itemCode> <ShipmentQuantity> <shipmentDate> - creates shipment record.");
		System.err.println("GetItems <itemCode> - gets items matching itemCode if they exist. % to return all");
		System.err.println("GetPurchases <itemCode> - gets purchases matching itemCode if they exist. % to return all");
		System.err.println("GetShipments <itemCode> - gets shipments matching itemCode if they exist. % to return all");
		System.err.println(
				"ItemsAvailable <itemCode> - gets quantity of available items matching itemCode on hand. % to return all");
		System.err.println("UpdateItem <itemCode> <price> - update the price of an item matching itemCode.");
		System.err.println(
				"DeleteItem <itemCode> - deletes an item matching itemCode if there are no purchases / shipments of the item.");
		System.err.println("DeletePurchase <itemCode> - deletes the most recent purchase matching itemCode.");
		System.err.println("DeleteShipment <itemCode> - deletes the most recent shipment matching itemCode.");
	}

	/**
	 * Main method
	 * 
	 * @param args - command line arguments to be parsed
	 */
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println();
			System.out.println("JDBC driver loaded");

			makeConnection();

			useDatabase("finalProject");

			switch (args[0]) {
			case "/?":
				printUsage();
				break;
			case "CreateItem":
				if (args.length == 4 && (Double.parseDouble(args[3]) >= 0)) {
					String itemCode = args[1];
					String itemDescription = args[2];
					String price = new DecimalFormat("0.00").format(Double.parseDouble(args[3]));

					CreateItem(itemCode, itemDescription, price);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify at least itemCode and price.");
					printUsage();
				}
				break;
			case "CreatePurchase":
				if (args.length == 3 && (Integer.parseInt(args[2]) > 0)) {
					String itemCode = args[1];
					int quantity = Integer.parseInt(args[2]);

					CreatePurchase(itemCode, quantity);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify itemCode and quantity.");
					printUsage();
				}
				break;
			case "CreateShipment":
				if (args.length == 4 && (Integer.parseInt(args[2]) > 0)) {
					String itemCode = args[1];
					int quantity = Integer.parseInt(args[2]);
					Date shipmentDate = Date.valueOf(args[3]);

					CreateShipment(itemCode, quantity, shipmentDate);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify itemCode, quantity and shipmentDate.");
					printUsage();
				}
				break;
			case "GetItems":
				if (args.length == 2) {
					String itemCode = args[1];

					GetItems(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode.");
					printUsage();
				}
				break;
			case "GetShipments":
				if (args.length == 2) {
					String itemCode = args[1];

					GetShipments(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode. Use % for all");
					printUsage();
				}
				break;
			case "GetPurchases":
				if (args.length == 2) {
					String itemCode = args[1];

					GetPurchases(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode. Use % for all");
					printUsage();
				}
				break;
			case "ItemsAvailable":
				if (args.length == 2) {
					String itemCode = args[1];

					ItemsAvailable(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode. Use % for all");
					printUsage();
				}
				break;
			case "UpdateItem":
				if (args.length == 3 && (Double.parseDouble(args[2]) >= 0)) {
					String itemCode = args[1];
					String price = new DecimalFormat("0.00").format(Double.parseDouble(args[2]));

					UpdateItem(itemCode, price);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode.");
					printUsage();
				}
				break;
			case "DeleteItem":
				if (args.length == 2) {
					String itemCode = args[1];

					DeleteItem(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode.");
					printUsage();
				}
				break;
			case "DeletePurchase":
				if (args.length == 2) {
					String itemCode = args[1];

					DeletePurchase(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode.");
					printUsage();
				}
				break;
			case "DeleteShipment":
				if (args.length == 2) {
					String itemCode = args[1];

					DeleteShipment(itemCode);

				} else {
					System.err.println("Wrong argument(s): Make sure to specify the correct itemCode.");
					printUsage();
				}
				break;
			default:
				printUsage();
				break;
			}

			System.out.println();
			System.out.println("Connection to " + database + " closed");
			System.out.println();
		} catch (Exception ex) {
			// handle the error
			System.err.println(ex);
		}
	}
}