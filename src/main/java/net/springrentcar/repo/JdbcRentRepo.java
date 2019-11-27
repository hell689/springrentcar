package net.springrentcar.repo;

import net.springrentcar.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcRentRepo implements RentRepo {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_FIND_ALL = "SELECT r.id, r.request, rq.color AS  request_color_id, c.color AS request_color, " +
            "rq.mark AS request_mark_id, m.mark AS request_mark, rq.volume AS request_volume, rq.gearbox AS request_gearbox_id, " +
            "g.gearbox AS request_gearbox, rq.start_date AS request_start_date, rq.end_date AS request_end_date, " +
            "rq.comment AS request_comment, rq.username AS request_username,   rq.processed AS request_processed, r.car, " +
            "cr.color AS car_color_id, cc.color AS car_color,  cr.mark AS car_mark_id, cm.mark AS car_mark, " +
            "cr.volume AS car_volume, cr.gearbox AS car_gearbox_id, cg.gearbox AS  car_gearbox, r.start_date, r.end_date  " +
            "FROM rents r LEFT JOIN requests rq ON r.request = rq.id LEFT JOIN colors c ON rq.color = c.id " +
            "LEFT JOIN marks m ON rq.mark = m.id LEFT JOIN gearboxes g ON rq.gearbox = g.id LEFT JOIN cars cr ON r.car = cr.id " +
            "LEFT JOIN colors cc ON cr.color = cc.id LEFT JOIN marks cm ON cr.mark = cm.id LEFT JOIN gearboxes cg ON cr.gearbox = cg.id";
    private final static String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE r.id=?";
    private final static String SQL_FIND_BY_USER = SQL_FIND_ALL + " WHERE r.request in (SELECT id FROM requests WHERE username = ?)";
    private static final String SQL_INSERT = "INSERT INTO rents(request, car, start_date, end_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE rents SET request = ?, car = ?, start_date =?, end_date = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM rents WHERE id = ?";


    public JdbcRentRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Rent> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new ResultSetExtractor<List<Rent>>() {
            @Override
            public List<Rent> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Rent> rents = new ArrayList<Rent>();
                while (rs.next()) {
                    Rent rent = new Rent();
                    rent.setId(rs.getLong("id"));
                    rent.setRequest(requestExtract(rs));
                    rent.setCar(carExtract(rs));
                    rent.setStartDate(rs.getDate("start_date"));
                    rent.setEndDate(rs.getDate("end_date"));
                    rents.add(rent);
                }
                return rents;
            }
        });
    }

    @Override
    public Rent findById(Long id) {
        return jdbcTemplate.query(SQL_FIND_BY_ID, new Object[]{id}, new ResultSetExtractor<Rent>() {
            @Override
            public Rent extractData(ResultSet rs) throws SQLException, DataAccessException {
                Rent rent = new Rent();
                rent.setId(rs.getLong("id"));
                rent.setRequest(requestExtract(rs));
                rent.setCar(carExtract(rs));
                rent.setStartDate(rs.getDate("start_date"));
                rent.setEndDate(rs.getDate("end_date"));
                return rent;
            }
        });
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public void insert(Rent rent) {
        jdbcTemplate.update(SQL_INSERT, rent.getRequest().getId(), rent.getCar().getId(), rent.getStartDate(), rent.getEndDate());
    }

    @Override
    public void update(Rent rent) {
        jdbcTemplate.update(SQL_UPDATE, rent.getRequest().getId(), rent.getCar().getId(), rent.getStartDate(), rent.getEndDate(), rent.getId());
    }

    @Override
    public List<Rent> findByUser(String username) {
        return jdbcTemplate.query(SQL_FIND_BY_USER, new ResultSetExtractor<List<Rent>>() {
            @Override
            public List<Rent> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Rent> rents = new ArrayList<Rent>();
                while (rs.next()) {
                    Rent rent = new Rent();
                    rent.setId(rs.getLong("id"));
                    rent.setRequest(requestExtract(rs));
                    rent.setCar(carExtract(rs));
                    rent.setStartDate(rs.getDate("start_date"));
                    rent.setEndDate(rs.getDate("end_date"));
                    rents.add(rent);
                }
                return rents;
            }
        }, username);
    }

    private Request requestExtract(ResultSet rs) throws SQLException, DataAccessException {
        Request request = new Request();
        request.setId(rs.getLong("request"));
        Mark mark = new Mark();
        mark.setId(rs.getLong("request_mark_id"));
        mark.setMark(rs.getString("request_mark"));
        request.setMark(mark);
        Gearbox gearbox = new Gearbox();
        gearbox.setId(rs.getLong("request_gearbox_id"));
        gearbox.setGearbox(rs.getString("request_gearbox"));
        request.setGearbox(gearbox);
        request.setVolume(rs.getFloat("request_volume"));
        Color color = new Color();
        color.setId(rs.getLong("request_color_id"));
        color.setColor(rs.getString("request_color"));
        request.setColor(color);
        request.setStartDate(rs.getDate("request_start_date"));
        request.setEndDate(rs.getDate("request_end_date"));
        request.setComment(rs.getString("request_comment"));
        request.setUsername(rs.getString("request_username"));
        request.setProcessed(rs.getBoolean("request_processed"));
        return request;
    }

    private Car carExtract(ResultSet rs) throws SQLException, DataAccessException {
        Car car = new Car();
        car.setId(rs.getLong("car"));
        Mark mark = new Mark();
        mark.setId(rs.getLong("car_mark_id"));
        mark.setMark(rs.getString("car_mark"));
        car.setMark(mark);
        Gearbox gearbox = new Gearbox();
        gearbox.setId(rs.getLong("car_gearbox_id"));
        gearbox.setGearbox(rs.getString("car_gearbox"));
        car.setGearbox(gearbox);
        car.setVolume(rs.getFloat("car_volume"));
        Color color = new Color();
        color.setId(rs.getLong("car_color_id"));
        color.setColor(rs.getString("car_color"));
        car.setColor(color);
        return car;
    }
}
