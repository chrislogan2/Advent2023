
# BAG HAS ONLY 12 red, 13 Green, 14 blue:

#12 red cubes, 13 green cubes, and 14 blue cubes.
bag = [12,13,14];
println("The bag has [",bag[1],"] Red, [",bag[2],"] Green, [",bag[3],"] Blue.")
game_id_regex = r"^Game\s(?<game_id>\d{1,5}):.*$";
game_substring_regex = r"^Game\s\d{1,5}:\s(?<gamedata>.*)$";

red_step_regex = r"(?<red_count>\d*)(?=\sred)";
green_step_regex = r"(?<green_count>\d*)(?=\sgreen)";
blue_step_regex = r"(?<blue_count>\d*)(?=\sblue)";
step_delim=";";
input_name = "input.txt"
input_path = joinpath(@__DIR__,input_name);
println(input_path);
global sum_power = 0;
for example in eachline(input_path)
#an example, we will read the real strings from file input.txt line by line 
#example = "Game 1: 13 green, 3 red; 4 red, 9 green, 4 blue; 9 green, 10 red, 2 blue";

id_match=match(game_id_regex, example);
game_match=match(game_substring_regex, example);
if id_match === nothing 
    println("Oops no Game ID or poorly formatted data?");
else
    cur_id = parse(Int32,id_match[:game_id]);
  #  println("The extracte game id is [",cur_id,"], it is a(n) ",typeof(cur_id));
end

if game_match ===nothing
    println("empty game or poorly formatted data?")
else
    local cur_game = game_match[:gamedata];
    local game_iter = eachsplit(cur_game,step_delim);
   #  x = iterate(game_iter);
    local possible = true
    local min_blocks = [0,0,0];
    for step in game_iter
        redmatch=match(red_step_regex, step);
        greenmatch=match(green_step_regex, step);
        bluematch = match(blue_step_regex,step);
        
        redcount = redmatch===nothing ? 0 : parse(Int32,redmatch[:red_count]);
        bluecount = bluematch===nothing ? 0 : parse(Int32,bluematch[:blue_count]);
        greencount = greenmatch===nothing ? 0 : parse(Int32,greenmatch[:green_count]);

        cur_vec = [redcount, greencount, bluecount];
        println("Red Blocks: [",redcount,"], Green Blocks: [", greencount,"], Blue Blocks: [" ,bluecount,"].");
        for index in eachindex(bag)
            min_blocks[index]= cur_vec[index] > min_blocks[index] ? cur_vec[index] : min_blocks[index]
        end
    end
    cur_power = min_blocks[1]*min_blocks[2]*min_blocks[3];
    println("Minimum blocks:[R,G,B] ",min_blocks, ". POWER: ", cur_power);
    global sum_power+=cur_power;
end
end
println("[",sum_power,"] Power sum")