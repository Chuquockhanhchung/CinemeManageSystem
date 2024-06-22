package dal;

import model.*;

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

    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAO(DBContext.getConn());
        ArrayList<Movie> movies = movieDAO.filter("", "", "2024-05-24");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public ArrayList<Movie> filter(String name, String status, String date) {
        ArrayList<Movie> movies = new ArrayList<>();
        name = "%" + name + "%";
        status = "%" + status + "%";
        date = "%" + date + "%";
        MovieTypeDAO tdao = new MovieTypeDAO(con);
        String sql = "SELECT * FROM movie_all  where MovieName LIKE ? And Status Like ? and ReleaseDate Like ? ;";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, status);
            ps.setString(3, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Movie a = new Movie();
                a.setMovieID(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setStatus(rs.getString(13));
                a.setReleaseDate(rs.getString(7));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
                movies.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public ArrayList<Movie> getall_Movie() {
        ArrayList<Movie> movies = new ArrayList<>();
        MovieTypeDAO tdao = new MovieTypeDAO(con);
        String sql = "SELECT * FROM movie_all;";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movie a = new Movie();
                a.setMovieID(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setType(rs.getString(3));
                a.setStatus(rs.getString(13));
                a.setReleaseDate(rs.getString(7));
                a.setDescription(rs.getString(4));
                a.setImage(rs.getString(10));
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
                c.setMovieID(rs.getInt(1));
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
                c.setMovieID(rs.getInt(1));
                c.setName(rs.getString(2));
                c.setDescription(rs.getString(3));
                c.setImage(rs.getString(4));
                c.setStatus(rs.getString(5));
                c.setDuration(rs.getInt(6));

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

    public ArrayList<Language> getAllLanguage() {
        ArrayList<Language> list = new ArrayList<>();
        String sql = "SELECT * FROM cinemamanagersystem.movielanguage;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Language l = new Language();
                l.setId(rs.getInt(1));
                l.setName(rs.getString(2));
                list.add(l);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<MovieType> getAllMovieType() {
        ArrayList<MovieType> list = new ArrayList<>();
        String sql = "SELECT * FROM cinemamanagersystem.movietype;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MovieType m = new MovieType();
                m.setTypeID(rs.getInt(1));
                m.setTypeName(rs.getString(2));
                m.setTypeDescription(rs.getString(3));
                list.add(m);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<Actor> getAllActor() {
        ArrayList<Actor> list = new ArrayList<>();
        String sql = "SELECT * FROM cinemamanagersystem.actors;";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Actor m = new Actor();
                m.setId(rs.getInt(1));
                m.setName(rs.getString(2));
                list.add(m);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void AddMovie(Movie m) {
        String sql = "insert into movie  (MovieName, Description, Director, ReleaseDate, Duration, Language, Image, status,Price)Values (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, m.getName());
            ps.setString(2, m.getDescription());
            ps.setString(3, m.getDirector());
            ps.setString(4, m.getReleaseDate());
            ps.setInt(5, m.getDuration());
            ps.setInt(6, m.getLanguages());
            ps.setString(7, m.getImage());
            ps.setString(8, m.getStatus());
            ps.setInt(9, m.getPrice());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddHasType(int a, int b) {
        String sql = "insert into movie_has_types (MovieID, TypeID) VALUES(?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddActor(String a) {
        String sql = "insert into actors ( ActorName) values(?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     public void AddType(String a) {
        String sql = "insert into movietype ( TypeName) values(?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, a);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void AddHasActor(int a, int b) {
        String sql = "insert into movie_has_actors (MovieID, ActorID) values(?,?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            ps.setInt(2, b);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int getIDMovie(){
        String sql ="SELECT max(MovieID) FROM movie ";
        int movieID = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                movieID = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return movieID;
    }
    
    public int getIdActor(){
        String sql ="SELECT max(ActorID) FROM cinemamanagersystem.actors";
        int actorID = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                actorID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return actorID;
    }
    
     public int getIdType(){
        String sql ="SELECT max(TypeID) FROM cinemamanagersystem.movietype";
        int typeID = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                typeID = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return typeID;
    }
    

}
