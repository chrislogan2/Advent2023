using System;
using System.Dynamic;
using System.Text.RegularExpressions;
namespace day5puzzle;
public class FieldResource
{
    public enum FieldResourceTypeName 
    {
        Seed,
        Soil,
        Fertilizer,
        Water,
        Light,
        Temperature,
        Humidity,
        Location,
        NOMATCH
    }
    private long resource_id=0;
    private FieldResourceTypeName resource_type= FieldResourceTypeName.Seed;
    public long ResourceId {
        get { return resource_id;}
        set {resource_id = value;}
    }
    public FieldResourceTypeName ResourceType {
        get {
            return resource_type;
        }
        set {
            resource_type = value;
        }
    }
    public FieldResource (long resourceId) {
        resource_id = resourceId;
        
    }
    public FieldResource (long resourceId, FieldResourceTypeName resourceType) {
        resource_id = resourceId;
        resource_type = resourceType;
    }
}