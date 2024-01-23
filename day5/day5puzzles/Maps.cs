using System;
using System.Dynamic;
using System.Text.RegularExpressions;
namespace day5puzzle;
public class Map
{
    public enum MapTypeName
    {
        SeedToSoil,
        SoilToFertilizer,
        FertilizerToWater,
        WaterToLight,
        LightToTemperature,
        TemperatureToHumidity,
        HumidityToLocation
    }
    
    private MapTypeName map_type;
    private FieldResource.FieldResourceTypeName dest_type, source_type;
    private long source_range_start=0;
    private long destination_range_start=0;
    private long range_size=0;
    public MapTypeName MapType {
        get {
            return map_type;
        }
        set {
            map_type = value;
        }
    }
    public FieldResource.FieldResourceTypeName DestType {
        get {
            return dest_type;
        }
        set {
            dest_type = value;
        }
    }
    public FieldResource.FieldResourceTypeName SourceType {
        get {
            return source_type;
        }
        set {
            source_type = value;
        }
    }
    public bool DestContains(long resourceId) {

        if (destination_range_start <= resourceId && resourceId < (destination_range_start+range_size)){
            return true;
        }else {
            return false;
        }
    }
    public bool SourceContains(long resourceId) {
        if (source_range_start <= resourceId && resourceId < (source_range_start+range_size)){
            return true;
        }else {
            return false;
        }
    }
    public FieldResource MapTypes(FieldResource res) {
        long newResId=0;
        FieldResource.FieldResourceTypeName newResType=FieldResource.FieldResourceTypeName.NOMATCH;

        if (res.ResourceType == dest_type) {
                
                if(DestContains(res.ResourceId)){
                    newResType = source_type;
                    newResId= res.ResourceId+source_range_start-destination_range_start;
                }
        }else if (res.ResourceType == source_type) {
            
            if(SourceContains(res.ResourceId)) {
                newResType = dest_type;
                newResId= res.ResourceId-source_range_start+destination_range_start;
            } 
        } else {
            newResId = 0;
        }
        FieldResource newRes = new FieldResource(newResId, newResType);
        return newRes;
    }
    public Map (long destStart, long sourceStart, long rangeSize, MapTypeName mapType) {
        destination_range_start = destStart;
        source_range_start = sourceStart;
        range_size = rangeSize;
        map_type = mapType;
        switch (mapType ){
            case MapTypeName.SeedToSoil: 
                source_type=FieldResource.FieldResourceTypeName.Seed;
                dest_type=FieldResource.FieldResourceTypeName.Soil;
                break;
            case MapTypeName.SoilToFertilizer:
                source_type=FieldResource.FieldResourceTypeName.Soil;
                dest_type=FieldResource.FieldResourceTypeName.Fertilizer;
                break;
            case MapTypeName.FertilizerToWater:
                source_type=FieldResource.FieldResourceTypeName.Fertilizer;
                dest_type=FieldResource.FieldResourceTypeName.Water;
                break;
            case MapTypeName.WaterToLight:
                source_type=FieldResource.FieldResourceTypeName.Water;
                dest_type=FieldResource.FieldResourceTypeName.Light;
                break;
            case MapTypeName.LightToTemperature:
                source_type=FieldResource.FieldResourceTypeName.Light;
                dest_type=FieldResource.FieldResourceTypeName.Temperature;
                break;
            case MapTypeName.TemperatureToHumidity:
                source_type=FieldResource.FieldResourceTypeName.Temperature;
                dest_type=FieldResource.FieldResourceTypeName.Humidity;
                break;
            case MapTypeName.HumidityToLocation:
                source_type=FieldResource.FieldResourceTypeName.Humidity;
                dest_type=FieldResource.FieldResourceTypeName.Location;
                break;
            default:
                break;
            
        }
    }

}
