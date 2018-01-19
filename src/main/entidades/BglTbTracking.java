/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author michael.beltran
 */
@Entity
@Table(name = "bgl_tb_tracking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BglTbTracking.findAll", query = "SELECT b FROM BglTbTracking b")
    , @NamedQuery(name = "BglTbTracking.findByCodEvento", query = "SELECT b FROM BglTbTracking b WHERE b.codEvento = :codEvento")
    , @NamedQuery(name = "BglTbTracking.findByCodTactividad", query = "SELECT b FROM BglTbTracking b WHERE b.codTactividad = :codTactividad")
    , @NamedQuery(name = "BglTbTracking.findByCodSitio", query = "SELECT b FROM BglTbTracking b WHERE b.codSitio = :codSitio")
    , @NamedQuery(name = "BglTbTracking.findByCodTipoem", query = "SELECT b FROM BglTbTracking b WHERE b.codTipoem = :codTipoem")
    , @NamedQuery(name = "BglTbTracking.findByCodAmbiente", query = "SELECT b FROM BglTbTracking b WHERE b.codAmbiente = :codAmbiente")
    , @NamedQuery(name = "BglTbTracking.findByTrkUsuario", query = "SELECT b FROM BglTbTracking b WHERE b.trkUsuario = :trkUsuario")
    , @NamedQuery(name = "BglTbTracking.findByTrkFecha", query = "SELECT b FROM BglTbTracking b WHERE b.trkFecha = :trkFecha")
    , @NamedQuery(name = "BglTbTracking.findByTrkObservacion", query = "SELECT b FROM BglTbTracking b WHERE b.trkObservacion = :trkObservacion")
    , @NamedQuery(name = "BglTbTracking.findByVehicleId", query = "SELECT b FROM BglTbTracking b WHERE b.vehicleId = :vehicleId")
    , @NamedQuery(name = "BglTbTracking.findByAlias", query = "SELECT b FROM BglTbTracking b WHERE b.alias = :alias")
    , @NamedQuery(name = "BglTbTracking.findByDay", query = "SELECT b FROM BglTbTracking b WHERE b.day = :day")
    , @NamedQuery(name = "BglTbTracking.findByMonth", query = "SELECT b FROM BglTbTracking b WHERE b.month = :month")
    , @NamedQuery(name = "BglTbTracking.findByYear", query = "SELECT b FROM BglTbTracking b WHERE b.year = :year")
    , @NamedQuery(name = "BglTbTracking.findByGpstime", query = "SELECT b FROM BglTbTracking b WHERE b.gpstime = :gpstime")
    , @NamedQuery(name = "BglTbTracking.findByDateTime", query = "SELECT b FROM BglTbTracking b WHERE b.dateTime = :dateTime")
    , @NamedQuery(name = "BglTbTracking.findByPcDate", query = "SELECT b FROM BglTbTracking b WHERE b.pcDate = :pcDate")
    , @NamedQuery(name = "BglTbTracking.findByPcTime", query = "SELECT b FROM BglTbTracking b WHERE b.pcTime = :pcTime")
    , @NamedQuery(name = "BglTbTracking.findByStreet", query = "SELECT b FROM BglTbTracking b WHERE b.street = :street")
    , @NamedQuery(name = "BglTbTracking.findByCity", query = "SELECT b FROM BglTbTracking b WHERE b.city = :city")
    , @NamedQuery(name = "BglTbTracking.findByState", query = "SELECT b FROM BglTbTracking b WHERE b.state = :state")
    , @NamedQuery(name = "BglTbTracking.findByZipCode", query = "SELECT b FROM BglTbTracking b WHERE b.zipCode = :zipCode")
    , @NamedQuery(name = "BglTbTracking.findByLatitude", query = "SELECT b FROM BglTbTracking b WHERE b.latitude = :latitude")
    , @NamedQuery(name = "BglTbTracking.findByLongitude", query = "SELECT b FROM BglTbTracking b WHERE b.longitude = :longitude")
    , @NamedQuery(name = "BglTbTracking.findBySpeed", query = "SELECT b FROM BglTbTracking b WHERE b.speed = :speed")
    , @NamedQuery(name = "BglTbTracking.findByCourse", query = "SELECT b FROM BglTbTracking b WHERE b.course = :course")
    , @NamedQuery(name = "BglTbTracking.findByAltitude", query = "SELECT b FROM BglTbTracking b WHERE b.altitude = :altitude")
    , @NamedQuery(name = "BglTbTracking.findByEvent", query = "SELECT b FROM BglTbTracking b WHERE b.event = :event")
    , @NamedQuery(name = "BglTbTracking.findByAdvisoryEvent", query = "SELECT b FROM BglTbTracking b WHERE b.advisoryEvent = :advisoryEvent")
    , @NamedQuery(name = "BglTbTracking.findByDistance", query = "SELECT b FROM BglTbTracking b WHERE b.distance = :distance")
    , @NamedQuery(name = "BglTbTracking.findBySatellites", query = "SELECT b FROM BglTbTracking b WHERE b.satellites = :satellites")
    , @NamedQuery(name = "BglTbTracking.findByGps", query = "SELECT b FROM BglTbTracking b WHERE b.gps = :gps")
    , @NamedQuery(name = "BglTbTracking.findByStateCode", query = "SELECT b FROM BglTbTracking b WHERE b.stateCode = :stateCode")
    , @NamedQuery(name = "BglTbTracking.findByInputs", query = "SELECT b FROM BglTbTracking b WHERE b.inputs = :inputs")
    , @NamedQuery(name = "BglTbTracking.findByAdvisoryInputs", query = "SELECT b FROM BglTbTracking b WHERE b.advisoryInputs = :advisoryInputs")
    , @NamedQuery(name = "BglTbTracking.findByOutputs", query = "SELECT b FROM BglTbTracking b WHERE b.outputs = :outputs")
    , @NamedQuery(name = "BglTbTracking.findByMessage", query = "SELECT b FROM BglTbTracking b WHERE b.message = :message")
    , @NamedQuery(name = "BglTbTracking.findByAdvisories", query = "SELECT b FROM BglTbTracking b WHERE b.advisories = :advisories")
    , @NamedQuery(name = "BglTbTracking.findByNearestPoint", query = "SELECT b FROM BglTbTracking b WHERE b.nearestPoint = :nearestPoint")
    , @NamedQuery(name = "BglTbTracking.findByAnalog1", query = "SELECT b FROM BglTbTracking b WHERE b.analog1 = :analog1")
    , @NamedQuery(name = "BglTbTracking.findByAnalog2", query = "SELECT b FROM BglTbTracking b WHERE b.analog2 = :analog2")
    , @NamedQuery(name = "BglTbTracking.findByAnalog3", query = "SELECT b FROM BglTbTracking b WHERE b.analog3 = :analog3")
    , @NamedQuery(name = "BglTbTracking.findByAnalog4", query = "SELECT b FROM BglTbTracking b WHERE b.analog4 = :analog4")
    , @NamedQuery(name = "BglTbTracking.findByHdop", query = "SELECT b FROM BglTbTracking b WHERE b.hdop = :hdop")
    , @NamedQuery(name = "BglTbTracking.findByVdop", query = "SELECT b FROM BglTbTracking b WHERE b.vdop = :vdop")
    , @NamedQuery(name = "BglTbTracking.findByGdop", query = "SELECT b FROM BglTbTracking b WHERE b.gdop = :gdop")
    , @NamedQuery(name = "BglTbTracking.findByPdop", query = "SELECT b FROM BglTbTracking b WHERE b.pdop = :pdop")
    , @NamedQuery(name = "BglTbTracking.findByRateOfClimb", query = "SELECT b FROM BglTbTracking b WHERE b.rateOfClimb = :rateOfClimb")
    , @NamedQuery(name = "BglTbTracking.findByCustom1", query = "SELECT b FROM BglTbTracking b WHERE b.custom1 = :custom1")
    , @NamedQuery(name = "BglTbTracking.findByCustom2", query = "SELECT b FROM BglTbTracking b WHERE b.custom2 = :custom2")
    , @NamedQuery(name = "BglTbTracking.findByCustom3", query = "SELECT b FROM BglTbTracking b WHERE b.custom3 = :custom3")
    , @NamedQuery(name = "BglTbTracking.findByCustom4", query = "SELECT b FROM BglTbTracking b WHERE b.custom4 = :custom4")
    , @NamedQuery(name = "BglTbTracking.findByCustom5", query = "SELECT b FROM BglTbTracking b WHERE b.custom5 = :custom5")
    , @NamedQuery(name = "BglTbTracking.findByCustom6", query = "SELECT b FROM BglTbTracking b WHERE b.custom6 = :custom6")
    , @NamedQuery(name = "BglTbTracking.findByCustomtext1", query = "SELECT b FROM BglTbTracking b WHERE b.customtext1 = :customtext1")
    , @NamedQuery(name = "BglTbTracking.findByCustomtext2", query = "SELECT b FROM BglTbTracking b WHERE b.customtext2 = :customtext2")
    , @NamedQuery(name = "BglTbTracking.findByNearbyStreets", query = "SELECT b FROM BglTbTracking b WHERE b.nearbyStreets = :nearbyStreets")
    , @NamedQuery(name = "BglTbTracking.findByAvailableInputs", query = "SELECT b FROM BglTbTracking b WHERE b.availableInputs = :availableInputs")
    , @NamedQuery(name = "BglTbTracking.findByAvailableOutputs", query = "SELECT b FROM BglTbTracking b WHERE b.availableOutputs = :availableOutputs")
    , @NamedQuery(name = "BglTbTracking.findByDriverId", query = "SELECT b FROM BglTbTracking b WHERE b.driverId = :driverId")
    , @NamedQuery(name = "BglTbTracking.findByVin", query = "SELECT b FROM BglTbTracking b WHERE b.vin = :vin")
    , @NamedQuery(name = "BglTbTracking.findByErrorCode", query = "SELECT b FROM BglTbTracking b WHERE b.errorCode = :errorCode")
    , @NamedQuery(name = "BglTbTracking.findByXml", query = "SELECT b FROM BglTbTracking b WHERE b.xml = :xml")
    , @NamedQuery(name = "BglTbTracking.findByCustomtext3", query = "SELECT b FROM BglTbTracking b WHERE b.customtext3 = :customtext3")
    , @NamedQuery(name = "BglTbTracking.findByExtendedInputs", query = "SELECT b FROM BglTbTracking b WHERE b.extendedInputs = :extendedInputs")
    , @NamedQuery(name = "BglTbTracking.findByAvailableExtendedInputs", query = "SELECT b FROM BglTbTracking b WHERE b.availableExtendedInputs = :availableExtendedInputs")
    , @NamedQuery(name = "BglTbTracking.findByOdometer", query = "SELECT b FROM BglTbTracking b WHERE b.odometer = :odometer")
    , @NamedQuery(name = "BglTbTracking.findByFuelLevel", query = "SELECT b FROM BglTbTracking b WHERE b.fuelLevel = :fuelLevel")
    , @NamedQuery(name = "BglTbTracking.findByBattery", query = "SELECT b FROM BglTbTracking b WHERE b.battery = :battery")
    , @NamedQuery(name = "BglTbTracking.findByOilLevel", query = "SELECT b FROM BglTbTracking b WHERE b.oilLevel = :oilLevel")
    , @NamedQuery(name = "BglTbTracking.findByOilTemperature", query = "SELECT b FROM BglTbTracking b WHERE b.oilTemperature = :oilTemperature")
    , @NamedQuery(name = "BglTbTracking.findByOilPressure", query = "SELECT b FROM BglTbTracking b WHERE b.oilPressure = :oilPressure")
    , @NamedQuery(name = "BglTbTracking.findByCoolantLevel", query = "SELECT b FROM BglTbTracking b WHERE b.coolantLevel = :coolantLevel")
    , @NamedQuery(name = "BglTbTracking.findByCoolantTemperature", query = "SELECT b FROM BglTbTracking b WHERE b.coolantTemperature = :coolantTemperature")
    , @NamedQuery(name = "BglTbTracking.findByFuelEconomy", query = "SELECT b FROM BglTbTracking b WHERE b.fuelEconomy = :fuelEconomy")
    , @NamedQuery(name = "BglTbTracking.findByAverageFuelEconomy", query = "SELECT b FROM BglTbTracking b WHERE b.averageFuelEconomy = :averageFuelEconomy")
    , @NamedQuery(name = "BglTbTracking.findByVehicleSpeed", query = "SELECT b FROM BglTbTracking b WHERE b.vehicleSpeed = :vehicleSpeed")
    , @NamedQuery(name = "BglTbTracking.findByEngineRpm", query = "SELECT b FROM BglTbTracking b WHERE b.engineRpm = :engineRpm")
    , @NamedQuery(name = "BglTbTracking.findByThrottlePosition", query = "SELECT b FROM BglTbTracking b WHERE b.throttlePosition = :throttlePosition")
    , @NamedQuery(name = "BglTbTracking.findBySensor1", query = "SELECT b FROM BglTbTracking b WHERE b.sensor1 = :sensor1")
    , @NamedQuery(name = "BglTbTracking.findBySensor2", query = "SELECT b FROM BglTbTracking b WHERE b.sensor2 = :sensor2")
    , @NamedQuery(name = "BglTbTracking.findBySensor3", query = "SELECT b FROM BglTbTracking b WHERE b.sensor3 = :sensor3")
    , @NamedQuery(name = "BglTbTracking.findBySensor4", query = "SELECT b FROM BglTbTracking b WHERE b.sensor4 = :sensor4")
    , @NamedQuery(name = "BglTbTracking.findBySensor5", query = "SELECT b FROM BglTbTracking b WHERE b.sensor5 = :sensor5")
    , @NamedQuery(name = "BglTbTracking.findBySensor6", query = "SELECT b FROM BglTbTracking b WHERE b.sensor6 = :sensor6")
    , @NamedQuery(name = "BglTbTracking.findBySensor7", query = "SELECT b FROM BglTbTracking b WHERE b.sensor7 = :sensor7")
    , @NamedQuery(name = "BglTbTracking.findBySensor8", query = "SELECT b FROM BglTbTracking b WHERE b.sensor8 = :sensor8")
    , @NamedQuery(name = "BglTbTracking.findByAuxBattery", query = "SELECT b FROM BglTbTracking b WHERE b.auxBattery = :auxBattery")
    , @NamedQuery(name = "BglTbTracking.findByEngineId", query = "SELECT b FROM BglTbTracking b WHERE b.engineId = :engineId")
    , @NamedQuery(name = "BglTbTracking.findByUpdateNumber", query = "SELECT b FROM BglTbTracking b WHERE b.updateNumber = :updateNumber")
    , @NamedQuery(name = "BglTbTracking.findByEngineHours", query = "SELECT b FROM BglTbTracking b WHERE b.engineHours = :engineHours")
    , @NamedQuery(name = "BglTbTracking.findByGeoobjects", query = "SELECT b FROM BglTbTracking b WHERE b.geoobjects = :geoobjects")
    , @NamedQuery(name = "BglTbTracking.findByCounty", query = "SELECT b FROM BglTbTracking b WHERE b.county = :county")
    , @NamedQuery(name = "BglTbTracking.findBySensor9", query = "SELECT b FROM BglTbTracking b WHERE b.sensor9 = :sensor9")
    , @NamedQuery(name = "BglTbTracking.findBySensor10", query = "SELECT b FROM BglTbTracking b WHERE b.sensor10 = :sensor10")
    , @NamedQuery(name = "BglTbTracking.findByBusType", query = "SELECT b FROM BglTbTracking b WHERE b.busType = :busType")
    , @NamedQuery(name = "BglTbTracking.findByBusField1", query = "SELECT b FROM BglTbTracking b WHERE b.busField1 = :busField1")
    , @NamedQuery(name = "BglTbTracking.findByBusField2", query = "SELECT b FROM BglTbTracking b WHERE b.busField2 = :busField2")
    , @NamedQuery(name = "BglTbTracking.findByBusField3", query = "SELECT b FROM BglTbTracking b WHERE b.busField3 = :busField3")
    , @NamedQuery(name = "BglTbTracking.findByBusField4", query = "SELECT b FROM BglTbTracking b WHERE b.busField4 = :busField4")
    , @NamedQuery(name = "BglTbTracking.findByBusField5", query = "SELECT b FROM BglTbTracking b WHERE b.busField5 = :busField5")
    , @NamedQuery(name = "BglTbTracking.findByFuelRate", query = "SELECT b FROM BglTbTracking b WHERE b.fuelRate = :fuelRate")
    , @NamedQuery(name = "BglTbTracking.findByReceivedFrom", query = "SELECT b FROM BglTbTracking b WHERE b.receivedFrom = :receivedFrom")
    , @NamedQuery(name = "BglTbTracking.findByDriver1", query = "SELECT b FROM BglTbTracking b WHERE b.driver1 = :driver1")
    , @NamedQuery(name = "BglTbTracking.findByDriver2", query = "SELECT b FROM BglTbTracking b WHERE b.driver2 = :driver2")
    , @NamedQuery(name = "BglTbTracking.findByAsset", query = "SELECT b FROM BglTbTracking b WHERE b.asset = :asset")
    , @NamedQuery(name = "BglTbTracking.findByMapSpeed", query = "SELECT b FROM BglTbTracking b WHERE b.mapSpeed = :mapSpeed")
    , @NamedQuery(name = "BglTbTracking.findByRtmxRxTime", query = "SELECT b FROM BglTbTracking b WHERE b.rtmxRxTime = :rtmxRxTime")
    , @NamedQuery(name = "BglTbTracking.findByIopRxTime", query = "SELECT b FROM BglTbTracking b WHERE b.iopRxTime = :iopRxTime")
    , @NamedQuery(name = "BglTbTracking.findByCodCiudad", query = "SELECT b FROM BglTbTracking b WHERE b.codCiudad = :codCiudad")})
public class BglTbTracking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COD_EVENTO")
    private Integer codEvento;
    @Column(name = "COD_TACTIVIDAD")
    private Integer codTactividad;
    @Column(name = "COD_SITIO")
    private Integer codSitio;
    @Column(name = "COD_TIPOEM")
    private Integer codTipoem;
    @Column(name = "COD_AMBIENTE")
    private Integer codAmbiente;
    @Column(name = "TRK_USUARIO")
    private String trkUsuario;
    @Column(name = "TRK_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trkFecha;
    @Column(name = "TRK_OBSERVACION")
    private String trkObservacion;
    @Column(name = "VEHICLE_ID")
    private String vehicleId;
    @Column(name = "ALIAS")
    private String alias;
    @Column(name = "DAY")
    private Integer day;
    @Column(name = "MONTH")
    private Integer month;
    @Column(name = "YEAR")
    private Integer year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "GPSTIME")
    private Float gpstime;
    @Column(name = "DATE_TIME")
    private Integer dateTime;
    @Column(name = "PC_DATE")
    private String pcDate;
    @Column(name = "PC_TIME")
    private String pcTime;
    @Column(name = "STREET")
    private String street;
    @Column(name = "CITY")
    private String city;
    @Column(name = "STATE")
    private String state;
    @Column(name = "ZIP_CODE")
    private String zipCode;
    @Column(name = "LATITUDE")
    private Float latitude;
    @Column(name = "LONGITUDE")
    private Float longitude;
    @Column(name = "SPEED")
    private Float speed;
    @Column(name = "COURSE")
    private Integer course;
    @Column(name = "ALTITUDE")
    private Float altitude;
    @Column(name = "EVENT")
    private Integer event;
    @Column(name = "ADVISORY_EVENT")
    private String advisoryEvent;
    @Column(name = "DISTANCE")
    private Float distance;
    @Column(name = "SATELLITES")
    private Integer satellites;
    @Column(name = "GPS")
    private String gps;
    @Column(name = "STATE_CODE")
    private Integer stateCode;
    @Column(name = "INPUTS")
    private Integer inputs;
    @Column(name = "ADVISORY_INPUTS")
    private String advisoryInputs;
    @Column(name = "OUTPUTS")
    private Integer outputs;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "ADVISORIES")
    private String advisories;
    @Column(name = "NEAREST_POINT")
    private String nearestPoint;
    @Column(name = "ANALOG1")
    private Integer analog1;
    @Column(name = "ANALOG2")
    private Integer analog2;
    @Column(name = "ANALOG3")
    private Integer analog3;
    @Column(name = "ANALOG4")
    private Integer analog4;
    @Column(name = "HDOP")
    private Float hdop;
    @Column(name = "VDOP")
    private Float vdop;
    @Column(name = "GDOP")
    private Float gdop;
    @Column(name = "PDOP")
    private Float pdop;
    @Column(name = "RATE_OF_CLIMB")
    private Float rateOfClimb;
    @Column(name = "CUSTOM1")
    private Integer custom1;
    @Column(name = "CUSTOM2")
    private Integer custom2;
    @Column(name = "CUSTOM3")
    private Integer custom3;
    @Column(name = "CUSTOM4")
    private Integer custom4;
    @Column(name = "CUSTOM5")
    private Integer custom5;
    @Column(name = "CUSTOM6")
    private Integer custom6;
    @Column(name = "CUSTOMTEXT1")
    private String customtext1;
    @Column(name = "CUSTOMTEXT2")
    private String customtext2;
    @Column(name = "NEARBY_STREETS")
    private String nearbyStreets;
    @Column(name = "AVAILABLE_INPUTS")
    private Integer availableInputs;
    @Column(name = "AVAILABLE_OUTPUTS")
    private Integer availableOutputs;
    @Column(name = "DRIVER_ID")
    private String driverId;
    @Column(name = "VIN")
    private String vin;
    @Column(name = "ERROR_CODE")
    private Integer errorCode;
    @Column(name = "XML")
    private String xml;
    @Column(name = "CUSTOMTEXT3")
    private String customtext3;
    @Column(name = "EXTENDED_INPUTS")
    private Integer extendedInputs;
    @Column(name = "AVAILABLE_EXTENDED_INPUTS")
    private Integer availableExtendedInputs;
    @Column(name = "ODOMETER")
    private Float odometer;
    @Column(name = "FUEL_LEVEL")
    private Float fuelLevel;
    @Column(name = "BATTERY")
    private Float battery;
    @Column(name = "OIL_LEVEL")
    private Float oilLevel;
    @Column(name = "OIL_TEMPERATURE")
    private Float oilTemperature;
    @Column(name = "OIL_PRESSURE")
    private Float oilPressure;
    @Column(name = "COOLANT_LEVEL")
    private Float coolantLevel;
    @Column(name = "COOLANT_TEMPERATURE")
    private Float coolantTemperature;
    @Column(name = "FUEL_ECONOMY")
    private Float fuelEconomy;
    @Column(name = "AVERAGE_FUEL_ECONOMY")
    private Float averageFuelEconomy;
    @Column(name = "VEHICLE_SPEED")
    private Float vehicleSpeed;
    @Column(name = "ENGINE_RPM")
    private Float engineRpm;
    @Column(name = "THROTTLE_POSITION")
    private Float throttlePosition;
    @Column(name = "SENSOR1")
    private Float sensor1;
    @Column(name = "SENSOR2")
    private Float sensor2;
    @Column(name = "SENSOR3")
    private Float sensor3;
    @Column(name = "SENSOR4")
    private Float sensor4;
    @Column(name = "SENSOR5")
    private Float sensor5;
    @Column(name = "SENSOR6")
    private Float sensor6;
    @Column(name = "SENSOR7")
    private Float sensor7;
    @Column(name = "SENSOR8")
    private Float sensor8;
    @Column(name = "AUX_BATTERY")
    private Integer auxBattery;
    @Column(name = "ENGINE_ID")
    private Integer engineId;
    @Column(name = "UPDATE_NUMBER")
    private Integer updateNumber;
    @Column(name = "ENGINE_HOURS")
    private Float engineHours;
    @Column(name = "GEOOBJECTS")
    private String geoobjects;
    @Column(name = "COUNTY")
    private String county;
    @Column(name = "SENSOR9")
    private Float sensor9;
    @Column(name = "SENSOR10")
    private Float sensor10;
    @Column(name = "BUS_TYPE")
    private Integer busType;
    @Column(name = "BUS_FIELD1")
    private Integer busField1;
    @Column(name = "BUS_FIELD2")
    private Integer busField2;
    @Column(name = "BUS_FIELD3")
    private Integer busField3;
    @Column(name = "BUS_FIELD4")
    private Integer busField4;
    @Column(name = "BUS_FIELD5")
    private Integer busField5;
    @Column(name = "FUEL_RATE")
    private Float fuelRate;
    @Column(name = "RECEIVED_FROM")
    private String receivedFrom;
    @Column(name = "DRIVER1")
    private String driver1;
    @Column(name = "DRIVER2")
    private String driver2;
    @Column(name = "ASSET")
    private String asset;
    @Column(name = "MAP_SPEED")
    private Integer mapSpeed;
    @Column(name = "RTMX_RX_TIME")
    private Integer rtmxRxTime;
    @Column(name = "IOP_RX_TIME")
    private Integer iopRxTime;
    @Column(name = "COD_CIUDAD")
    private String codCiudad;

    public BglTbTracking() {
    }

    public BglTbTracking(Integer codEvento) {
        this.codEvento = codEvento;
    }

    public Integer getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(Integer codEvento) {
        this.codEvento = codEvento;
    }

    public Integer getCodTactividad() {
        return codTactividad;
    }

    public void setCodTactividad(Integer codTactividad) {
        this.codTactividad = codTactividad;
    }

    public Integer getCodSitio() {
        return codSitio;
    }

    public void setCodSitio(Integer codSitio) {
        this.codSitio = codSitio;
    }

    public Integer getCodTipoem() {
        return codTipoem;
    }

    public void setCodTipoem(Integer codTipoem) {
        this.codTipoem = codTipoem;
    }

    public Integer getCodAmbiente() {
        return codAmbiente;
    }

    public void setCodAmbiente(Integer codAmbiente) {
        this.codAmbiente = codAmbiente;
    }

    public String getTrkUsuario() {
        return trkUsuario;
    }

    public void setTrkUsuario(String trkUsuario) {
        this.trkUsuario = trkUsuario;
    }

    public Date getTrkFecha() {
        return trkFecha;
    }

    public void setTrkFecha(Date trkFecha) {
        this.trkFecha = trkFecha;
    }

    public String getTrkObservacion() {
        return trkObservacion;
    }

    public void setTrkObservacion(String trkObservacion) {
        this.trkObservacion = trkObservacion;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getGpstime() {
        return gpstime;
    }

    public void setGpstime(Float gpstime) {
        this.gpstime = gpstime;
    }

    public Integer getDateTime() {
        return dateTime;
    }

    public void setDateTime(Integer dateTime) {
        this.dateTime = dateTime;
    }

    public String getPcDate() {
        return pcDate;
    }

    public void setPcDate(String pcDate) {
        this.pcDate = pcDate;
    }

    public String getPcTime() {
        return pcTime;
    }

    public void setPcTime(String pcTime) {
        this.pcTime = pcTime;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getAdvisoryEvent() {
        return advisoryEvent;
    }

    public void setAdvisoryEvent(String advisoryEvent) {
        this.advisoryEvent = advisoryEvent;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Integer getSatellites() {
        return satellites;
    }

    public void setSatellites(Integer satellites) {
        this.satellites = satellites;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getInputs() {
        return inputs;
    }

    public void setInputs(Integer inputs) {
        this.inputs = inputs;
    }

    public String getAdvisoryInputs() {
        return advisoryInputs;
    }

    public void setAdvisoryInputs(String advisoryInputs) {
        this.advisoryInputs = advisoryInputs;
    }

    public Integer getOutputs() {
        return outputs;
    }

    public void setOutputs(Integer outputs) {
        this.outputs = outputs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdvisories() {
        return advisories;
    }

    public void setAdvisories(String advisories) {
        this.advisories = advisories;
    }

    public String getNearestPoint() {
        return nearestPoint;
    }

    public void setNearestPoint(String nearestPoint) {
        this.nearestPoint = nearestPoint;
    }

    public Integer getAnalog1() {
        return analog1;
    }

    public void setAnalog1(Integer analog1) {
        this.analog1 = analog1;
    }

    public Integer getAnalog2() {
        return analog2;
    }

    public void setAnalog2(Integer analog2) {
        this.analog2 = analog2;
    }

    public Integer getAnalog3() {
        return analog3;
    }

    public void setAnalog3(Integer analog3) {
        this.analog3 = analog3;
    }

    public Integer getAnalog4() {
        return analog4;
    }

    public void setAnalog4(Integer analog4) {
        this.analog4 = analog4;
    }

    public Float getHdop() {
        return hdop;
    }

    public void setHdop(Float hdop) {
        this.hdop = hdop;
    }

    public Float getVdop() {
        return vdop;
    }

    public void setVdop(Float vdop) {
        this.vdop = vdop;
    }

    public Float getGdop() {
        return gdop;
    }

    public void setGdop(Float gdop) {
        this.gdop = gdop;
    }

    public Float getPdop() {
        return pdop;
    }

    public void setPdop(Float pdop) {
        this.pdop = pdop;
    }

    public Float getRateOfClimb() {
        return rateOfClimb;
    }

    public void setRateOfClimb(Float rateOfClimb) {
        this.rateOfClimb = rateOfClimb;
    }

    public Integer getCustom1() {
        return custom1;
    }

    public void setCustom1(Integer custom1) {
        this.custom1 = custom1;
    }

    public Integer getCustom2() {
        return custom2;
    }

    public void setCustom2(Integer custom2) {
        this.custom2 = custom2;
    }

    public Integer getCustom3() {
        return custom3;
    }

    public void setCustom3(Integer custom3) {
        this.custom3 = custom3;
    }

    public Integer getCustom4() {
        return custom4;
    }

    public void setCustom4(Integer custom4) {
        this.custom4 = custom4;
    }

    public Integer getCustom5() {
        return custom5;
    }

    public void setCustom5(Integer custom5) {
        this.custom5 = custom5;
    }

    public Integer getCustom6() {
        return custom6;
    }

    public void setCustom6(Integer custom6) {
        this.custom6 = custom6;
    }

    public String getCustomtext1() {
        return customtext1;
    }

    public void setCustomtext1(String customtext1) {
        this.customtext1 = customtext1;
    }

    public String getCustomtext2() {
        return customtext2;
    }

    public void setCustomtext2(String customtext2) {
        this.customtext2 = customtext2;
    }

    public String getNearbyStreets() {
        return nearbyStreets;
    }

    public void setNearbyStreets(String nearbyStreets) {
        this.nearbyStreets = nearbyStreets;
    }

    public Integer getAvailableInputs() {
        return availableInputs;
    }

    public void setAvailableInputs(Integer availableInputs) {
        this.availableInputs = availableInputs;
    }

    public Integer getAvailableOutputs() {
        return availableOutputs;
    }

    public void setAvailableOutputs(Integer availableOutputs) {
        this.availableOutputs = availableOutputs;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getCustomtext3() {
        return customtext3;
    }

    public void setCustomtext3(String customtext3) {
        this.customtext3 = customtext3;
    }

    public Integer getExtendedInputs() {
        return extendedInputs;
    }

    public void setExtendedInputs(Integer extendedInputs) {
        this.extendedInputs = extendedInputs;
    }

    public Integer getAvailableExtendedInputs() {
        return availableExtendedInputs;
    }

    public void setAvailableExtendedInputs(Integer availableExtendedInputs) {
        this.availableExtendedInputs = availableExtendedInputs;
    }

    public Float getOdometer() {
        return odometer;
    }

    public void setOdometer(Float odometer) {
        this.odometer = odometer;
    }

    public Float getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(Float fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Float getBattery() {
        return battery;
    }

    public void setBattery(Float battery) {
        this.battery = battery;
    }

    public Float getOilLevel() {
        return oilLevel;
    }

    public void setOilLevel(Float oilLevel) {
        this.oilLevel = oilLevel;
    }

    public Float getOilTemperature() {
        return oilTemperature;
    }

    public void setOilTemperature(Float oilTemperature) {
        this.oilTemperature = oilTemperature;
    }

    public Float getOilPressure() {
        return oilPressure;
    }

    public void setOilPressure(Float oilPressure) {
        this.oilPressure = oilPressure;
    }

    public Float getCoolantLevel() {
        return coolantLevel;
    }

    public void setCoolantLevel(Float coolantLevel) {
        this.coolantLevel = coolantLevel;
    }

    public Float getCoolantTemperature() {
        return coolantTemperature;
    }

    public void setCoolantTemperature(Float coolantTemperature) {
        this.coolantTemperature = coolantTemperature;
    }

    public Float getFuelEconomy() {
        return fuelEconomy;
    }

    public void setFuelEconomy(Float fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    public Float getAverageFuelEconomy() {
        return averageFuelEconomy;
    }

    public void setAverageFuelEconomy(Float averageFuelEconomy) {
        this.averageFuelEconomy = averageFuelEconomy;
    }

    public Float getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Float vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public Float getEngineRpm() {
        return engineRpm;
    }

    public void setEngineRpm(Float engineRpm) {
        this.engineRpm = engineRpm;
    }

    public Float getThrottlePosition() {
        return throttlePosition;
    }

    public void setThrottlePosition(Float throttlePosition) {
        this.throttlePosition = throttlePosition;
    }

    public Float getSensor1() {
        return sensor1;
    }

    public void setSensor1(Float sensor1) {
        this.sensor1 = sensor1;
    }

    public Float getSensor2() {
        return sensor2;
    }

    public void setSensor2(Float sensor2) {
        this.sensor2 = sensor2;
    }

    public Float getSensor3() {
        return sensor3;
    }

    public void setSensor3(Float sensor3) {
        this.sensor3 = sensor3;
    }

    public Float getSensor4() {
        return sensor4;
    }

    public void setSensor4(Float sensor4) {
        this.sensor4 = sensor4;
    }

    public Float getSensor5() {
        return sensor5;
    }

    public void setSensor5(Float sensor5) {
        this.sensor5 = sensor5;
    }

    public Float getSensor6() {
        return sensor6;
    }

    public void setSensor6(Float sensor6) {
        this.sensor6 = sensor6;
    }

    public Float getSensor7() {
        return sensor7;
    }

    public void setSensor7(Float sensor7) {
        this.sensor7 = sensor7;
    }

    public Float getSensor8() {
        return sensor8;
    }

    public void setSensor8(Float sensor8) {
        this.sensor8 = sensor8;
    }

    public Integer getAuxBattery() {
        return auxBattery;
    }

    public void setAuxBattery(Integer auxBattery) {
        this.auxBattery = auxBattery;
    }

    public Integer getEngineId() {
        return engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public Integer getUpdateNumber() {
        return updateNumber;
    }

    public void setUpdateNumber(Integer updateNumber) {
        this.updateNumber = updateNumber;
    }

    public Float getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(Float engineHours) {
        this.engineHours = engineHours;
    }

    public String getGeoobjects() {
        return geoobjects;
    }

    public void setGeoobjects(String geoobjects) {
        this.geoobjects = geoobjects;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Float getSensor9() {
        return sensor9;
    }

    public void setSensor9(Float sensor9) {
        this.sensor9 = sensor9;
    }

    public Float getSensor10() {
        return sensor10;
    }

    public void setSensor10(Float sensor10) {
        this.sensor10 = sensor10;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public Integer getBusField1() {
        return busField1;
    }

    public void setBusField1(Integer busField1) {
        this.busField1 = busField1;
    }

    public Integer getBusField2() {
        return busField2;
    }

    public void setBusField2(Integer busField2) {
        this.busField2 = busField2;
    }

    public Integer getBusField3() {
        return busField3;
    }

    public void setBusField3(Integer busField3) {
        this.busField3 = busField3;
    }

    public Integer getBusField4() {
        return busField4;
    }

    public void setBusField4(Integer busField4) {
        this.busField4 = busField4;
    }

    public Integer getBusField5() {
        return busField5;
    }

    public void setBusField5(Integer busField5) {
        this.busField5 = busField5;
    }

    public Float getFuelRate() {
        return fuelRate;
    }

    public void setFuelRate(Float fuelRate) {
        this.fuelRate = fuelRate;
    }

    public String getReceivedFrom() {
        return receivedFrom;
    }

    public void setReceivedFrom(String receivedFrom) {
        this.receivedFrom = receivedFrom;
    }

    public String getDriver1() {
        return driver1;
    }

    public void setDriver1(String driver1) {
        this.driver1 = driver1;
    }

    public String getDriver2() {
        return driver2;
    }

    public void setDriver2(String driver2) {
        this.driver2 = driver2;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Integer getMapSpeed() {
        return mapSpeed;
    }

    public void setMapSpeed(Integer mapSpeed) {
        this.mapSpeed = mapSpeed;
    }

    public Integer getRtmxRxTime() {
        return rtmxRxTime;
    }

    public void setRtmxRxTime(Integer rtmxRxTime) {
        this.rtmxRxTime = rtmxRxTime;
    }

    public Integer getIopRxTime() {
        return iopRxTime;
    }

    public void setIopRxTime(Integer iopRxTime) {
        this.iopRxTime = iopRxTime;
    }

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEvento != null ? codEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BglTbTracking)) {
            return false;
        }
        BglTbTracking other = (BglTbTracking) object;
        if ((this.codEvento == null && other.codEvento != null) || (this.codEvento != null && !this.codEvento.equals(other.codEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "main.entidades.BglTbTracking[ codEvento=" + codEvento + " ]";
    }
    
}
