// See https://aka.ms/new-console-template for more information
using System;
using System.Dynamic;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
namespace day5puzzle;
public class Test
{
    /// <summary>
    /// demo seed array count =4
    /// j=0, j=2, done
    /// if j<count-1
    /// </summary>
    private static string _demo_puzzle_data = @"seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4

";
            private static Regex seed_regex = new Regex(@"^seeds:\s(?<SeedList>[\s\d]+)$",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex seed_soil_map_regex = new Regex(@"^seed-to-soil map:[\n\r](?<SeedSoilMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex soil_fertilizer_map_regex = new Regex(@"^soil-to-fertilizer map:[\n\r](?<SoilFertilizerMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex fertilizer_water_map_regex = new Regex(@"^fertilizer-to-water map:[\n\r](?<FertilizerWaterMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex water_light_map_regex = new Regex(@"^water-to-light map:[\n\r](?<WaterLightMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex light_temperature_map_regex = new Regex(@"^light-to-temperature map:[\n\r](?<LightTemperatureMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex temperature_humidity_map_regex = new Regex(@"^temperature-to-humidity map:[\n\r](?<TemperatureHumidtyMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private static Regex humidity_location_map_regex = new Regex(@"^humidity-to-location map:[\n\r](?<HumidityLocationMap>[\d\s]+)[\n\r]",RegexOptions.Compiled| RegexOptions.Multiline );
            private List <FieldResource> seeds = new List<FieldResource>();
            private List <Map> maps = new List<Map>();
            private List <long> seeds2 = new List<long>();
            private List <List<FieldResource>> resources = new List<List<FieldResource>>();
    public List<FieldResource> Seeds {
        get{ return seeds;}
        set{ }
    }
    public List<List<FieldResource>> Resources {
        get { return resources;}
        set {}
    }
    public List<Map> Maps {
        get{ return maps;}
        set {}
    }
    public string PuzzleData {
        get{return _demo_puzzle_data;} 
        set{ _demo_puzzle_data = value;}
    }
    private void ParseSeeds (string inputData) {
        MatchCollection seed_matches = seed_regex.Matches(inputData);
        CaptureCollection seed_captures = seed_matches[0].Groups[1].Captures;
        Console.WriteLine("{0} captures in the demo data, they are:\n",seed_captures.Count);
        for (int i=0; i < seed_captures.Count; i++) {
            Console.WriteLine("The Capture {0} is:\n{1}\n",i,seed_captures[i].Value );
            var cap = seed_captures[i].Value.Trim().Split();
            for (int j=0; j< cap.Count(); j++){
                var curSeed = new FieldResource(long.Parse(cap[j].Trim()), FieldResource.FieldResourceTypeName.Seed);
                seeds.Add(curSeed);
                Console.WriteLine("Seed {0}, resType: {1}",curSeed.ResourceId, curSeed.ResourceType);
            }
        }
    }
        private void ParseSeeds2 (string inputData) {
        MatchCollection seed_matches = seed_regex.Matches(inputData);
        CaptureCollection seed_captures = seed_matches[0].Groups[1].Captures;
        Console.WriteLine("{0} captures in the demo data, they are:\n",seed_captures.Count);
        for (int i=0; i < seed_captures.Count; i++) {
            //Console.WriteLine("The Capture {0} is:\n{1}\n",i,seed_captures[i].Value );
            var cap = seed_captures[i].Value.Trim().Split();
            for (int j=0; j< cap.Count()-1; j=j+2){
                for(long k=0; k < long.Parse(cap[j+1]); k++){
                    var curSeed = new FieldResource(long.Parse(cap[j].Trim())+k, FieldResource.FieldResourceTypeName.Seed);
                    //seeds.Add(curSeed);
                    seeds2.Add(long.Parse(cap[j].Trim())+k);
                   // Console.WriteLine("Seed {0}, resType: {1}",curSeed.ResourceId, curSeed.ResourceType);

                }
                
            }
        }
    }
    private void ParseMap(string inputData, Map.MapTypeName mapType) {
        Console.WriteLine("Parsing Maps for  {0}", mapType);
        MatchCollection matches;
        switch (mapType){
            case Map.MapTypeName.SeedToSoil:
                matches = seed_soil_map_regex.Matches(inputData);

                break;
            case Map.MapTypeName.SoilToFertilizer:
                matches = soil_fertilizer_map_regex.Matches(inputData);
                break;
            case Map.MapTypeName.FertilizerToWater:
                matches = fertilizer_water_map_regex.Matches(inputData);
                break;
            case Map.MapTypeName.WaterToLight:
                matches = water_light_map_regex.Matches(inputData);
                break;
            case Map.MapTypeName.LightToTemperature:
                matches = light_temperature_map_regex.Matches(inputData);
                break;
            case Map.MapTypeName.TemperatureToHumidity:
                matches = temperature_humidity_map_regex.Matches(inputData);
                break;
            case Map.MapTypeName.HumidityToLocation:
                matches = humidity_location_map_regex.Matches(inputData);
                break;
            default:
                throw new ArgumentNullException("is not a parsed map type currently");
                break;
                        }
        CaptureCollection captures = matches[0].Groups[1].Captures;
        //List <Map> maps =new List<Map>();
       // Console.WriteLine("{0} captures in the demo data, they are:\n",captures.Count);
        for (int i=0; i < captures.Count; i++) {
           // Console.WriteLine("The Capture {0} is:\n{1}\n",i,captures[i].Value );
            var cap = captures[i].Value.Split(Environment.NewLine);
           // Console.WriteLine("There are {0} Lines (maps) of type {1}.\n",cap.Count(), mapType);
            for(int j=0; j < cap.Count()-1; j++){
                var splits = cap[j].Trim().Split();
                //Console.WriteLine("{0} things to split, {1},{2},{3}",splits.Count(), splits[0],splits[1],splits[2]);
                maps.Add( 
                    new Map(long.Parse(
                        splits[0].Trim()),
                        long.Parse(splits[1].Trim()),
                        long.Parse(splits[2].Trim()),
                        mapType));
            }

        }

    }
    private void ParseAll(string inputData) {
            ParseSeeds(inputData);
            ParseMap(inputData, Map.MapTypeName.SeedToSoil);
            ParseMap(inputData, Map.MapTypeName.SoilToFertilizer);
            ParseMap(inputData, Map.MapTypeName.FertilizerToWater);
            ParseMap(inputData, Map.MapTypeName.WaterToLight);
            ParseMap(inputData, Map.MapTypeName.LightToTemperature);
            ParseMap(inputData, Map.MapTypeName.TemperatureToHumidity);
            ParseMap(inputData, Map.MapTypeName.HumidityToLocation);
    }
        private void ParseAll2(string inputData) {
            ParseSeeds2(inputData);
            ParseMap(inputData, Map.MapTypeName.SeedToSoil);
            ParseMap(inputData, Map.MapTypeName.SoilToFertilizer);
            ParseMap(inputData, Map.MapTypeName.FertilizerToWater);
            ParseMap(inputData, Map.MapTypeName.WaterToLight);
            ParseMap(inputData, Map.MapTypeName.LightToTemperature);
            ParseMap(inputData, Map.MapTypeName.TemperatureToHumidity);
            ParseMap(inputData, Map.MapTypeName.HumidityToLocation);
    }
    private void MapResources() {
        Console.WriteLine("Mapping all resources? (start with filtering the mapslist)");
        long mins = 0;
        var minlist = new ConcurrentBag<long>();

        //List<FieldResource> locs = new List<FieldResource>();
        var filtered_seedsoil=from map in maps
						where map.MapType == Map.MapTypeName.SeedToSoil
                        select map;
        var filtered_soilfert =from map in maps
						where map.MapType == Map.MapTypeName.SoilToFertilizer
                        select map;
        var filtered_fertwater =from map in maps
						where map.MapType == Map.MapTypeName.FertilizerToWater
                        select map;
        var filtered_waterlight =from map in maps
						where map.MapType == Map.MapTypeName.WaterToLight
                        select map;
        var filtered_lighttemp=from map in maps
						where map.MapType == Map.MapTypeName.LightToTemperature
                        select map;
        var filtered_temphumid =from map in maps
						where map.MapType == Map.MapTypeName.TemperatureToHumidity
                        select map;
        var filtered_humidloc =from map in maps
						where map.MapType == Map.MapTypeName.HumidityToLocation
                        select map;
        Console.WriteLine("Done filtering maps");
        Parallel.ForEach(seeds2, seed =>
            
        //foreach(long seed in seeds2) {
            {
            //List<FieldResource> res_list = new List<FieldResource>();
            FieldResource prev_res = new FieldResource(seed,FieldResource.FieldResourceTypeName.Seed);
            FieldResource next_res = new FieldResource(seed,FieldResource.FieldResourceTypeName.Soil);
                // the defuault case.
            foreach(Map map in filtered_seedsoil){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                    //Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }

            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Fertilizer);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.SoilToFertilizer
            //            select map;

            foreach(Map map in filtered_soilfert){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                //    Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
           // Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Water);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.FertilizerToWater
            //            select map;
            foreach(Map map in filtered_fertwater){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                //    Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Light);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.WaterToLight
             //           select map;
            foreach(Map map in filtered_waterlight){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                //    Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Temperature);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.LightToTemperature
            //            select map;
            foreach(Map map in filtered_lighttemp){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                //    Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Humidity);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.TemperatureToHumidity
            //            select map;
            foreach(Map map in filtered_temphumid){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                //    Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            prev_res = next_res;
            next_res = new FieldResource(next_res.ResourceId,FieldResource.FieldResourceTypeName.Location);

            //filtered =from map in maps
			//			where map.MapType == Map.MapTypeName.HumidityToLocation
             //           select map;
            foreach(Map map in filtered_humidloc){
                
                var res = map.MapTypes(prev_res);
                if (res.ResourceType != FieldResource.FieldResourceTypeName.NOMATCH){
                    ////Console.WriteLine("{3} {0} Maps to {1}: {2}",prev_res.ResourceId,res.ResourceType, res.ResourceId,prev_res.ResourceType);
                    next_res = res;
                }
            }
            //Console.WriteLine("This {2} matched {0} {1}.",next_res.ResourceId, next_res.ResourceType, prev_res.ResourceType);
            //res_list.Add(next_res);
            //locs.Add(next_res);
            //if(mins == 0 || mins > next_res.ResourceId){
            //    mins = next_res.ResourceId;
            //}
            minlist.Add(next_res.ResourceId);
        });
        //var minimum = from loc in locs 
        //    select loc.ResourceId;
        //Console.WriteLine("{0} is minimum",minimum.Min());
        Console.WriteLine("{0} is minimum", minlist.Min());
    }

    public void puzzle1_test1 ()
    {
            FieldResource x = new FieldResource(22, FieldResource.FieldResourceTypeName.Seed);
            Map y = new Map(20, 98, 10, Map.MapTypeName.SeedToSoil);
            FieldResource resultmap = y.MapTypes(x);

            FormattableString a_string = $"the original resource is \n {x.ResourceType} {x.ResourceId}\n This maps to {resultmap.ResourceType} {resultmap.ResourceId}";
            //Console.WriteLine(x.ResourceType.ToString());
            ParseSeeds(_demo_puzzle_data);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.SeedToSoil);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.SoilToFertilizer);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.FertilizerToWater);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.WaterToLight);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.LightToTemperature);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.TemperatureToHumidity);
            ParseMap(_demo_puzzle_data, Map.MapTypeName.HumidityToLocation);

            Console.WriteLine(FormattableString.Invariant(a_string));


            //String example_data = 
            // String[] x = example_data.Split("seed-to-soil map:");
            //Console.WriteLine(_demo_puzzle_data.Split("seed-to-soil map:"));
   
    }
     public void puzzle1_test2 ()
    {
            var puzzle_data = File.ReadAllText("input.txt");
            if (puzzle_data == null) {
                throw new FileLoadException("Couldn't load input.txt");
            }
            //ParseAll2(_demo_puzzle_data);
            ParseAll2(puzzle_data);
            MapResources();
            Console.WriteLine("There are {0} maps.\n There are {1} seeds.",maps.Count(), seeds.Count());

    }
}
