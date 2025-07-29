import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SimpleVehicleService {

    // In-memory booking storage
    static List<String> bookings = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // Create HTTP server at port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // /book endpoint - creates a service booking
        server.createContext("/book", new HttpHandler() {
            public void handle(HttpExchange exchange) {
                try {
                    if ("GET".equals(exchange.getRequestMethod())) {
                        String booking = "Booking#" + (bookings.size() + 1);
                        bookings.add(booking);

                        String response = "Service booked: " + booking;
                        exchange.sendResponseHeaders(200, response.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes());
                        os.close();
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        exchange.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // /bookings endpoint - lists all bookings
        server.createContext("/bookings", new HttpHandler() {
            public void handle(HttpExchange exchange) {
                try {
                    if ("GET".equals(exchange.getRequestMethod())) {
                        StringBuilder response = new StringBuilder();
                        for (String b : bookings) {
                            response.append(b).append("\n");
                        }

                        byte[] respBytes = response.toString().getBytes();
                        exchange.sendResponseHeaders(200, respBytes.length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(respBytes);
                        os.close();
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        exchange.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }
}