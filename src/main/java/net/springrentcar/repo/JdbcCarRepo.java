package net.springrentcar.repo;

import net.springrentcar.domain.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcCarRepo implements CarRepo {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private static final String SQL_FIND_ALL = "SELECT c.id, m.id AS mark_id, m.mark, g.id AS gearbox_id, g.gearbox, " +
            "c.volume, cl.id AS color_id, cl.color FROM cars c LEFT JOIN marks m ON c.mark = m.id " +
            "LEFT JOIN gearboxes g ON c.gearbox = g.id LEFT JOIN colors cl ON c.color = cl.id ";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE c.id = ?";
    private static final String SQL_FIND_FREE_CARS = SQL_FIND_ALL + " WHERE c.id NOT IN (SELECT car FROM rents where end_date > ?)";
    private static final String SQL_FIND_SUITABLE_COLOR = SQL_FIND_ALL + " WHERE c.color = ?";
    private static final String SQL_FIND_SUITABLE_MARK = SQL_FIND_ALL + " WHERE c.mark = ?";
    private static final String SQL_FIND_SUITABLE_GEARBOX = SQL_FIND_ALL + " WHERE c.gearbox = ?";
    private static final String SQL_FIND_SUITABLE_VOLUME = SQL_FIND_ALL + " WHERE c.volume = ?";
    private static final String SQL_INSERT = "INSERT INTO cars(mark, gearbox, volume, color) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE cars SET mark = ?, gearbox = ?, volume = ?, color = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM cars WHERE id = ?";

    public JdbcCarRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Car> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new ResultSetExtractor<List<Car>>() {
            @Override
            public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Car> cars = new ArrayList<Car>();
                while (rs.next()) {
                    cars.add(carExtract(rs));
                }
                return cars;
            }
        });
    }

    @Override
    public Car findById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new CarMapper(), id);
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    @Override
    public void insert(Car car) {
        jdbcTemplate.update(SQL_INSERT, car.getMark().getId(), car.getGearbox().getId(), car.getVolume(),
                car.getColor().getId());
    }

    @Override
    public void update(Car car) {
        jdbcTemplate.update(SQL_UPDATE, car.getMark().getId(), car.getGearbox().getId(), car.getVolume(),
                car.getColor().getId(), car.getId());
    }

    @Override
    public List<Car> findFreeCar(Date date) {
        return jdbcTemplate.query(SQL_FIND_FREE_CARS, new Object[]{date}, new ResultSetExtractor<List<Car>>() {
            @Override
            public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Car> cars = new ArrayList<>();
                while (rs.next()) {
                    cars.add(carExtract(rs));
                }
                return cars;
            }
        });
    }

    @Override
    public List<Car> findSuitableCars(Request request) {
        Map<String, List<Car>> suitableCars = new HashMap<>();
        if (request.getColor() != null) {
            List<Car> sameColorCars = jdbcTemplate.query(SQL_FIND_SUITABLE_COLOR, new Long[]{request.getColor().getId()},
                    new ResultSetExtractor<List<Car>>() {
                        @Override
                        public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            List<Car> cars = new ArrayList<Car>();
                            while (rs.next()) {
                                cars.add(carExtract(rs));
                            }
                            return cars;
                        }
                    });
            if (sameColorCars.size() > 0)
                suitableCars.put("Color", sameColorCars);
        }
        if (request.getGearbox() != null) {
            List<Car> sameGearboxCars = jdbcTemplate.query(SQL_FIND_SUITABLE_GEARBOX, new Long[]{request.getGearbox().getId()},
                    new ResultSetExtractor<List<Car>>() {
                        @Override
                        public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            List<Car> cars = new ArrayList<Car>();
                            while (rs.next()) {
                                cars.add(carExtract(rs));
                            }
                            return cars;
                        }
                    });
            suitableCars.put("Gearbox", sameGearboxCars);
        }
        if (request.getMark() != null) {
            List<Car> sameMarkCars = jdbcTemplate.query(SQL_FIND_SUITABLE_MARK, new Long[]{request.getMark().getId()},
                    new ResultSetExtractor<List<Car>>() {
                        @Override
                        public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            List<Car> cars = new ArrayList<Car>();
                            while (rs.next()) {
                                cars.add(carExtract(rs));
                            }
                            return cars;
                        }
                    });
            if (sameMarkCars.size() > 0)
                suitableCars.put("Mark", sameMarkCars);
        }
        if (request.getVolume() != 0f) {
            List<Car> sameVolumeCars = jdbcTemplate.query(SQL_FIND_SUITABLE_VOLUME, new Float[]{request.getVolume()},
                    new ResultSetExtractor<List<Car>>() {
                        @Override
                        public List<Car> extractData(ResultSet rs) throws SQLException, DataAccessException {
                            List<Car> cars = new ArrayList<Car>();
                            while (rs.next()) {
                                cars.add(carExtract(rs));
                            }
                            return cars;
                        }
                    });
            if (sameVolumeCars.size() > 0)
                suitableCars.put("Volume", sameVolumeCars);
        }

        Map<Car, Integer> mostSuitableCars = new HashMap<>();
        for (Map.Entry<String, List<Car>> entry : suitableCars.entrySet()) {
            for (Car car : entry.getValue()) {
                Integer count = mostSuitableCars.get(car);
                if (count == null) {
                    count = 0;
                }
                mostSuitableCars.put(car, ++count);
            }
        }
        List<Car> sameCars = new ArrayList<>();
        for (Map.Entry<Car, Integer> entry : mostSuitableCars.entrySet()) {
            if (entry.getValue() >= 2)
                sameCars.add(entry.getKey());
        }
        suitableCars.put("Most suitable cars", sameCars);
        return sameCars;
    }

    private Car carExtract(ResultSet rs) throws SQLException, DataAccessException {
        Car car = new Car();
        car.setId(rs.getLong("id"));
        Mark mark = new Mark();
        mark.setId(rs.getLong("mark_id"));
        mark.setMark(rs.getString("mark"));
        car.setMark(mark);
        Gearbox gearbox = new Gearbox();
        gearbox.setId(rs.getLong("gearbox_id"));
        gearbox.setGearbox(rs.getString("gearbox"));
        car.setGearbox(gearbox);
        car.setVolume(rs.getFloat("volume"));
        Color color = new Color();
        color.setId(rs.getLong("color_id"));
        color.setColor(rs.getString("color"));
        car.setColor(color);
        return car;
    }

    private class CarMapper implements RowMapper<Car> {
        @Override
        public Car mapRow(ResultSet rs, int i) throws SQLException {
            return carExtract(rs);
        }
    }
}
