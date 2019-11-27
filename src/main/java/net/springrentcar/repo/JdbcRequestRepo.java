package net.springrentcar.repo;

import net.springrentcar.domain.Color;
import net.springrentcar.domain.Gearbox;
import net.springrentcar.domain.Mark;
import net.springrentcar.domain.Request;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcRequestRepo implements RequestRepo {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private final static String SQL_FIND_ALL = "SELECT r.id, m.id AS mark_id, m.mark, g.id AS gearbox_id, g.gearbox, " +
            "r.volume, cl.id AS color_id, cl.color, r.start_date, r.end_date, r.comment, r.username, r.processed " +
            "FROM requests r LEFT JOIN marks m ON r.mark = m.id  LEFT JOIN gearboxes g ON r.gearbox = g.id " +
            "LEFT JOIN colors cl ON r.color = cl.id ";
    private final static String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE r.id=?";
    private final static String SQL_FIND_BY_USER = SQL_FIND_ALL + " WHERE r.username = ?";
    private static final String SQL_INSERT = "INSERT INTO requests(mark, gearbox, volume, color, start_date, end_date, " +
            "comment, username, processed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE requests SET mark = ?, gearbox = ?, volume = ?, color = ?, start_date =?," +
            " end_date = ?, comment = ?, username = ?, processed = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM requests WHERE id = ?";


    public JdbcRequestRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Request> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new ResultSetExtractor<List<Request>>() {
            @Override
            public List<Request> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Request> requests = new ArrayList<Request>();
                while (rs.next()) {
                    requests.add(requestExtract(rs));
                }
                return requests;
            }
        });
    }

    @Override
    public Request findById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new RequestMapper(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public void insert(Request request) {
        jdbcTemplate.update(SQL_INSERT, request.getMark().getId(), request.getGearbox().getId(), request.getVolume(),
                request.getColor().getId(), request.getStartDate(), request.getEndDate(), request.getComment(),
                request.getUsername(), request.isProcessed());
    }

    @Override
    public void update(Request request) {
        jdbcTemplate.update(SQL_UPDATE, request.getMark().getId(), request.getGearbox().getId(), request.getVolume(),
                request.getColor().getId(), request.getStartDate(), request.getEndDate(), request.getComment(),
                request.getUsername(), request.isProcessed(), request.getId());
    }

    @Override
    public List<Request> findUserRequests(String username) {
        return jdbcTemplate.query(SQL_FIND_BY_USER, new ResultSetExtractor<List<Request>>() {
            @Override
            public List<Request> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Request> requests = new ArrayList<Request>();
                while (rs.next()) {
                    requests.add(requestExtract(rs));
                }
                return requests;
            }
        }, username);
    }

    private Request requestExtract(ResultSet rs) throws SQLException, DataAccessException {
        Request request = new Request();
        request.setId(rs.getLong("id"));
        Mark mark = new Mark();
        mark.setId(rs.getLong("mark_id"));
        mark.setMark(rs.getString("mark"));
        request.setMark(mark);
        Gearbox gearbox = new Gearbox();
        gearbox.setId(rs.getLong("gearbox_id"));
        gearbox.setGearbox(rs.getString("gearbox"));
        request.setGearbox(gearbox);
        request.setVolume(rs.getFloat("volume"));
        Color color = new Color();
        color.setId(rs.getLong("color_id"));
        color.setColor(rs.getString("color"));
        request.setColor(color);
        request.setStartDate(rs.getDate("start_date"));
        request.setEndDate(rs.getDate("end_date"));
        request.setComment(rs.getString("comment"));
        request.setUsername(rs.getString("username"));
        request.setProcessed(rs.getBoolean("processed"));
        return request;
    }

    private class RequestMapper implements RowMapper<Request> {

        @Override
        public Request mapRow(ResultSet rs, int i) throws SQLException {
            return requestExtract(rs);
        }
    }
}
