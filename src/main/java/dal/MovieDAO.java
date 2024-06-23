package dal;

import model.Account;
import model.Movie;
import model.ShowTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MovieDAO extends DBContext {
    private final Connection con;

    public MovieDAO(Connection con) {
        super();
        this.con = con;
    }

    public ArrayList<String> getMovieType(){
        ArrayList<String> type = new ArrayList<>();
        String sql = "select movietype.TypeName from cinemamanagersystem.movietype;";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                type.add(rs.getString("TypeName"));
            }
            return type;

        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Movie> FilterbyType(String type){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where Types like ?;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+type+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(14));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> SearchMovie(String Search){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where MovieName like ?;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+Search+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(14));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> SearchMovie(String Search, String Status){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where MovieName like ? and Status like ?;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+Search+"%");
            ps.setString(2, "%"+Status+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(14));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> phim(String status){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where Status like ?;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+status+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(11));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> phim(String status, String Type){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where Status like ? and Types like ?;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%"+status+"%");
            ps.setString(2, "%"+Type+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(11));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public double GetRateByID (int id){
        String sql="SELECT sum(Rate), count(Rate) from feedback where MovieID = ? and rate>0;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return (double) rs.getDouble(1)/rs.getDouble(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Movie> film(){
        ArrayList<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movie_all where IMDbRating > 3.5;";
        try {PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(11));
                a.setRate(rs.getFloat(12));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void main(String[] args) {
        MovieDAO type = new MovieDAO(DBContext.getConn());
        ArrayList<Movie> St = type.phim("Đang chiếu");
        ArrayList<Movie> St2 = type.phim("Sắp chiếu");
        for(int i=0; i<St.size(); i++) {
            System.out.println(St.get(i).toString());
        }
        System.out.println();
        for(int i=0; i<St2.size(); i++) {
            System.out.println(St2.get(i).toString());
        }
    }

    public ArrayList<Movie> getall_Movie() {
        ArrayList<Movie> movies = new ArrayList<>();
        MovieTypeDAO tdao = new MovieTypeDAO(con);
        String sql = "SELECT * FROM movie_all;";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movie a = new Movie();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                a.setTrailer(rs.getString(11));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> getMovieType(int id) {
        String sql = "select m.MovieID, m.MovieName, m.Description, t.TypeName, m.Image, m.Status,  m.Duration  from movietype t join (SELECT m.MovieID, m.MovieName, m.Description, t.TypeID, m.Image, m.Status, m.Duration FROM movie m join movie_has_types t on m.MovieID = t.MovieID) m on m.TypeID = t.TypeID   where m.MovieID = ? ";
        ArrayList<Movie> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie c = new Movie();
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDescription(rs.getString(3));
                c.setType(rs.getString(4));
                c.setImage(rs.getString(5));
                c.setStatus(rs.getString(6));
                c.setDuration(rs.getInt(7));
                list.add(c);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public Movie getMovie(int id) {
        String sql = "SELECT m.MovieID, m.MovieName, m.Description, m.Image, m.Status, m.Duration FROM movie m join movie_has_types t on m.MovieID = t.MovieID where m.MovieID =? ";
        Movie c = new Movie();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                c.setId(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDescription(rs.getString(3));
                c.setImage(rs.getString(4));
                c.setStatus(rs.getString(5));
                c.setDuration(rs.getInt(6));
                c.setTrailer(rs.getString(14));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;

    }

//

    public ArrayList<ShowTime> getShowTime(int id) {
        ArrayList<ShowTime> list = new ArrayList<>();
        String sql = " SELECT s.ShowtimeID,s.RoomID, s.MovieID, CASE DAYOFWEEK(s.showdate)\n"
                + "        WHEN 1 THEN 'CN'\n"
                + "        WHEN 2 THEN 'T2'\n"
                + "        WHEN 3 THEN 'T3'\n"
                + "        WHEN 4 THEN 'T4'\n"
                + "        WHEN 5 THEN 'T5'\n"
                + "        WHEN 6 THEN 'T6'\n"
                + "        WHEN 7 THEN 'T7'\n"
                + "    END  as thu, s.Duration, s.MaxTickets, day(s.showdate) as day, month(s.showdate) as month, date_format(s.starttime, '%H:%i') as time,  s.ShowDate  FROM showtime s where s.MovieID = ? and s.StartTime >=current_date()\n"
                + "  ";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowTime show = new ShowTime();
                show.setShowTimeID(rs.getInt(1));
                show.setRoomID(rs.getInt(2));
                show.setMovieID(rs.getInt(3));
                show.setDayName(rs.getString(4));
                show.setDuration(rs.getInt(5));
                show.setMaxTickets(rs.getInt(6));
                show.setDay(rs.getInt(7));
                show.setMonth(rs.getInt(8));
                show.setTime(rs.getString(9));
                show.setDate(rs.getString(10));
                list.add(show);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<ShowTime> getTime(int id) {
        ArrayList<ShowTime> list = new ArrayList<>();
        String sql = " SELECT  distinct s.ShowDate ,CASE DAYOFWEEK(s.showdate)\n"
                + "        WHEN 1 THEN 'CN'\n"
                + "        WHEN 2 THEN 'T2'\n"
                + "        WHEN 3 THEN 'T3'\n"
                + "        WHEN 4 THEN 'T4'\n"
                + "        WHEN 5 THEN 'T5'\n"
                + "        WHEN 6 THEN 'T6'\n"
                + "        WHEN 7 THEN 'T7'\n"
                + "    END AS dayname, Day(s.showdate) as day, month(s.showdate) as month  FROM showtime s where s.MovieID = ? and s.StartTime >=current_date()";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShowTime show = new ShowTime();
                show.setTime(rs.getString(1));
                show.setDayName(rs.getString(2));
                show.setDay(rs.getInt(3));
                show.setMonth(rs.getInt(4));
                list.add(show);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
