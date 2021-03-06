

var width, height;

var nodes = [];
var links = [];

function initialize(statesStr, transitionsStr, w, h){

	states = JSON.parse(statesStr);
	transitions = JSON.parse(transitionsStr);

	width = w;
	height = h;

	nodes = [];
	links = [];

	states.forEach(function(state) {
		nodes.push( state );
	});

	transitions.forEach(function(transition) {
		sources = nodes.filter( function(x) {return x.name === transition.from;} );
		targets = nodes.filter( function(x) {return x.name === transition.to;} );
		links.push({"source":sources[0], "target":targets[0], type:"directed"});
	});
}

function visualize(){

	d3.selectAll("svg").remove();

	var force = d3.layout.force()
		.nodes(d3.values(nodes))
		.links(links)
		.size([width, height])
		.linkDistance(60)
		.charge(-300)
		.on("tick", tick)
		.start();

	var svg = d3.select("body").append("svg")
		.attr("width", width)
		.attr("height", height);

	// Per-type markers, as they don't inherit styles.
	svg.append("defs").selectAll("marker")
		.data(["directed", "directed_dotted", "directed_green"])
	  .enter().append("marker")
		.attr("id", function(d) { return d; })
		.attr("viewBox", "0 -5 10 10")
		.attr("refX", 15)
		.attr("refY", -1.5)
		.attr("markerWidth", 6)
		.attr("markerHeight", 6)
		.attr("orient", "auto")
	  .append("path")
		.attr("d", "M0,-5L10,0L0,5");

	var path = svg.append("g").selectAll("path")
		.data(force.links())
	  .enter().append("path")
		.attr("class", function(d) { return "link " + d.type; })
		.attr("marker-end", function(d) { return "url(#" + d.type + ")"; });

	var circle = svg.append("g").selectAll("circle")
		.data(force.nodes())
	  .enter().append("circle")
		.attr("r", 6)
        .attr("class", function(d) { return d.type; })
		.call(force.drag);

	var text = svg.append("g").selectAll("text")
		.data(force.nodes())
	  .enter().append("text")
		.attr("x", 8)
		.attr("y", ".31em")
		.text(function(d) { return d.name; });

	// Use elliptical arc path segments to doubly-encode directionality.
	function tick() {
	  path.attr("d", linkArc);
	  circle.attr("transform", transform);
	  text.attr("transform", transform);
	}

	function linkArc(d) {
	  var dx = d.target.x - d.source.x,
		  dy = d.target.y - d.source.y,
		  dr = Math.sqrt(dx * dx + dy * dy);
	  return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
	}

	function transform(d) {
	  return "translate(" + d.x + "," + d.y + ")";
	}

}
