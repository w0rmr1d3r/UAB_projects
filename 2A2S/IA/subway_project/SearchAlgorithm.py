# This file contains all the required routines to make an A* search algorithm.
#
__authors__='RamonManuIbai'
__group__='DX19.07'
# _________________________________________________________________________________________
# Intel.ligencia Artificial 
# Grau en Enginyeria Informatica
# Curs 2015-2016
# Universitat Autonoma de Barcelona
# _________________________________________________________________________________________

from SubwayMap import *
import math
import bisect


class Node:
    # __init__ Constructor of Node Class.
    def __init__(self, station, father):
        """
        __init__: 	Constructor of the Node class
        :param
                - station: STATION information of the Station of this Node
                - father: NODE (see Node definition) of his father
        """
        
        self.station = station      # STATION information of the Station of this Node
        self.g = 0                  # REAL cost - depending on the type of preference -
                                    # to get from the origin to this Node
        self.h = 0                  # REAL heuristic value to get from the origin to this Node
        self.f = 0                  # REAL evaluate function
        self.parentsID = []         # TUPLE OF NODES (from the origin to its father)
        self.father = father        # NODE pointer to his father
        self.time = 0               # REAL time required to get from the origin to this Node
                                    # [optional] Only useful for GUI
        self.num_stopStation = 0    # INTEGER number of stops stations made from the origin to this Node
                                    # [optional] Only useful for GUI
        self.walk = 0               # REAL distance made from the origin to this Node
                                    # [optional] Only useful for GUI
        self.transfers = 0          # INTEGER number of transfers made from the origin to this Node
                                    # [optional] Only useful for GUI

    def __str__(self):
        return str(self.station.id) + ","

    def setEvaluation(self):
        """
        setEvaluation: 	Calculates the Evaluation Function
        :returns
                - f: REAL evaluate function
        """
        self.f = self.g + self.h

    def setHeuristic(self, typePreference, node_destination,city):
        """"
        setHeuristic: 	Calculates the heuristic depending on the preference selected
        :params
                - typePreference: INTEGER Value to indicate the preference selected: 
                                0 - Null Heuristic
                                1 - minimum Time
                                2 - minimum Distance 
                                3 - minimum Transfers
                                4 - minimum Stops
                - node_destination: PATH of the destination station
                - city: CITYINFO with the information of the city (see CityInfo class definition)
        """
        aux_h = 0
        # Time: Euclidian distance divided city max velocity
        if typePreference == 1:
            distance = math.sqrt(math.pow(node_destination.station.x - self.station.x, 2) + math.pow(node_destination.station.y - self.station.y, 2))
            time = distance / city.max_velocity
            aux_h = time
        # Distance: Euclidian distance
        elif typePreference == 2:
            distance = math.sqrt(math.pow(node_destination.station.x - self.station.x, 2) + math.pow(node_destination.station.y - self.station.y, 2))
            aux_h = distance
        # MinTransfers: Equals 1 if the line is not the same, otherwise it is 0
        elif typePreference == 3:
            if self.station.line != node_destination.station.line:
                aux_h = 1
        # MinStops: Equals 1 if the stations are adjacent, otherwise we suppose it will be the next station, therefore 2
        elif typePreference == 4:
            nodes = self.station.destinationDic.keys()
            if node_destination.station.id in nodes:
                aux_h = 1
            else:
                aux_h = 2
        self.h = aux_h

    def setRealCost(self,  costTable):
        """
        setRealCost: 	Calculates the real cost depending on the preference selected
        :params
                 - costTable: DICTIONARY. Relates each station with their adjacency an their real cost. NOTE that this
                             cost can be in terms of any preference.
        """
        aux_g = 0
        try:
            for parent in self.parentsID:
                if parent.father is not None:
                    aux_g += costTable[parent.station.id][parent.father.station.id]
            if self.father is not None:
                aux_g += costTable[self.station.id][self.father.station.id]
        except KeyError:
            pass
        self.g = aux_g

INFINITY = float("inf")


def Expand(fatherNode, stationList, typePreference, node_destination, costTable,city):
    """
        Expand: It expands a node and returns the list of connected stations (childrenList)
        :params
                - fatherNode: NODE of the current node that should be expanded
                - stationList: LIST of the stations of a city. (- id, destinationDic, name, line, x, y -)
                - typePreference: INTEGER Value to indicate the preference selected:
                                0 - Null Heuristic
                                1 - minimum Time
                                2 - minimum Distance
                                3 - minimum Transfers
                                4 - minimum Stops
                - node_destination: NODE (see Node definition) of the destination
                - costTable: DICTIONARY. Relates each station with their adjacency an their real cost. NOTE that this
                             cost can be in terms of any preference.
                - city: CITYINFO with the information of the city (see CityInfo class definition)
        :returns
                - childrenList:  LIST of the set of child Nodes for this current node (fatherNode)

    """
    childrenList = []

    for child in costTable[fatherNode.station.id].keys():
            aux_node = Node(stationList[child - 1], fatherNode)

            # save parents path
            for node in fatherNode.parentsID:
                aux_node.parentsID.append(node)
            aux_node.parentsID.append(fatherNode)

            # calculates h, g, f
            aux_node.setHeuristic(typePreference, node_destination, city)
            aux_node.setRealCost(costTable)
            aux_node.setEvaluation()

            # save information about path into the aux_node
            time = aux_node.station.destinationDic[fatherNode.station.id]
            if aux_node.station.line == fatherNode.station.line:
                aux_node.transfers = fatherNode.transfers
            else:
                aux_node.transfers = fatherNode.transfers + 1

            distance = city.velocity_lines[fatherNode.station.line - 1] * time
            aux_node.time = fatherNode.time + time
            aux_node.num_stopStation = fatherNode.num_stopStation
            if aux_node.station.name != fatherNode.station.name:
                aux_node.num_stopStation += 1
                aux_node.walk = fatherNode.walk + distance
            else:
                aux_node.walk = fatherNode.walk

            childrenList.append(aux_node)
    return childrenList


def RemoveCycles(childrenList):
    """
        RemoveCycles: It removes from childrenList the set of childrens that include some cycles in their path.
        :params
                - childrenList: LIST of the set of child Nodes for a certain Node
        :returns
                - listWithoutCycles:  LIST of the set of child Nodes for a certain Node which not includes cycles
    """
    listWithoutCycles = []
    for children in childrenList:
        found = False
        i = 0
        num_parents = len(children.parentsID)
        while not found and i < num_parents:
            if children.station.id == children.parentsID[i].station.id:
                found = True
            else:
                i += 1
        if not found:
            listWithoutCycles.append(children)

    return listWithoutCycles


def RemoveRedundantPaths(list_expanded, listPaths, tcp):
    """
        RemoveRedundantPaths:   It removes the Redundant Paths. They are not optimal solution!
                                If a node is visited and have a lower g in this moment, TCP is updated.
                                In case of having a higher value, we should remove this child.
                                If a node is not yet visited, we should include to the TCP.
        :params
                - list_expanded: LIST childs generated by processed node
                - listPaths (by reference): LIST OF LISTS A* processing list
                - tcp: DICTIONARY saves g of processed childs indexed by node station id

        :returns
                - listWithoutReduntantPaths: LIST with redundant nodes deleted

    """
    g = None
    listWithoutReduntantPaths = []
    for node in list_expanded:
        delete = False
        # we look if there is or not into the tcp, taking the saved g from tcp if it exists
        try:
            g = tcp[node.station.id]
        except KeyError:
            g = None
        # if not founded, we add it to the tcp
        if g is None:
            tcp[node.station.id] = node.g
        else:
            # if we find a better g, we update it
            if node.g < g:
                tcp[node.station.id] = node.g
                # we look for the worst route to delete it from the A* list
                for i, l in enumerate(listPaths):
                    if l[0].station.id == node.station.id:
                        del listPaths[i]
            else:
                delete = True
        # if the node doesn't need to be deleted, we append it to the list
        if not delete:
            listWithoutReduntantPaths.append(node)
    return listWithoutReduntantPaths


def setCostTable( typePreference, stationList,city):
    """
    setCostTable :      Real cost of a travel.
    :param
            - typePreference: INTEGER Value to indicate the preference selected:
                                0 - Adjacency
                                1 - minimum Time
                                2 - minimum Distance
                                3 - minimum Transfers
                                4 - minimum Stops
            - stationList: LIST of the stations of a city. (- id, destinationDic, name, line, x, y -)
            - city: CITYINFO with the information of the city (see CityInfo class definition)
    :return:
            - costTable: DICTIONARY. Relates each station with their adjacency an their g, depending on the
                                 type of Preference Selected.
    """
    costTable = {}
    if typePreference == 1:
        for station in stationList:
            for pos in city.adjacency[station.id].keys():
                station2 = stationList[pos - 1]
                time = station.destinationDic[station2.id]
                if not costTable.has_key(station.id):
                    costTable[station.id] = {}
                costTable[station.id][station2.id] = time
                if not costTable.has_key(station2.id):
                    costTable[station2.id] = {}
                costTable[station2.id][station.id] = time

    elif typePreference == 2:
        for station in stationList:
            for pos in city.adjacency[station.id].keys():
                station2 = stationList[pos - 1]
                time = station.destinationDic[station2.id]
                distance = city.velocity_lines[station.line - 1] * time
                if station.name == station2.name:
                    distance = 0
                if not costTable.has_key(station.id):
                    costTable[station.id] = {}
                costTable[station.id][station2.id] = distance
                if not costTable.has_key(station2.id):
                    costTable[station2.id] = {}
                costTable[station2.id][station.id] = distance

    elif typePreference == 3:
        for station in stationList:
            for pos in city.adjacency[station.id].keys():
                station2 = stationList[pos - 1]
                if station.line == station2.line:
                    trans = 0
                else:
                    trans = 1
                if not costTable.has_key(station.id):
                    costTable[station.id] = {}
                costTable[station.id][station2.id] = trans
                if not costTable.has_key(station2.id):
                    costTable[station2.id] = {}
                costTable[station2.id][station.id] = trans

    elif typePreference == 4:
        for station in stationList:
            for pos in city.adjacency[station.id].keys():
                station2 = stationList[pos - 1]
                if station.line == station2.line:
                    stops = 1
                else:
                    stops = 0
                if not costTable.has_key(station.id):
                    costTable[station.id] = {}
                costTable[station.id][station2.id] = stops
                if not costTable.has_key(station2.id):
                    costTable[station2.id] = {}
                costTable[station2.id][station.id] = stops

    return costTable


def coord2station(coord, stationList):
    """
    coord2station :      From coordinates, it searches the closest stations.
    :param
            - coord:  LIST of two REAL values, which refer to the coordinates of a point in the city.
            - stationList: LIST of the stations of a city. (- id, destinationDic, name, line, x, y -)

    :return:
            - possible_origins: List of the Indexes of the stationList structure, which corresponds to the closest
            station
    """
    possible_origins = []
    x = coord[0]
    y = coord[1]
    # sets default min distance to the first station distance
    min_distance = math.sqrt(math.pow(stationList[0].x - x, 2) + math.pow(stationList[0].y - y, 2))
    possible_origins.append(0)
    for i in range(1, len(stationList) - 1, 1):
        distance = math.sqrt(math.pow(stationList[i].x - x, 2) + math.pow(stationList[i].y - y, 2))
        if distance == min_distance:
            possible_origins.append(i)
        if distance < min_distance:
            possible_origins = []
            possible_origins.append(i)
            min_distance = distance
    return possible_origins


def getStationsIds(realPath):
    """
    getStationsIds : Saves in a list the stations id of the real path from the origin to the destination
    :param
            - realPath:  LIST of NODES of the best founded solution in A* [0] Origin -> [n] Destination.

    :return:
            - stations_ids: List of the stations id [0] id destination -> [n] id origin
    """
    stations_ids = []
    for node in reversed(realPath):
        stations_ids.append(node.station.id)
    return stations_ids


def insertOrderedByF(E, processing_path, listPaths, typePreference):
    """
    insertOrderedByF : Saves in a list the stations id of the real path from the origin to the destination
    :param
            - E:  LIST of NODES of the best founded solution in A* [0] Origin -> [n] Destination.
            - processing_path: LIST path actually processing
            - listPaths (by reference): LIST OF LISTS A* processing list

    """
    for child in E:
        found = False
        i = 0
        num_paths = len(listPaths)
        while not found and (i < num_paths):
            if listPaths[i][0].f > child.f:
                found = True
            else:
                i += 1
        listPaths.insert(i, [child] + processing_path)


def AstarAlgorithm(stationList, coord_origin, coord_destination, typePreference, city, flag_redundants):
    """
     AstarAlgorithm: main function. It is the connection between the GUI and the AStar search code.
     INPUTS:
            - stationList: LIST of the stations of a city. (- id, name, destinationDic, line, x, y -)
            - coord_origin: TUPLE of two values referring to the origin coordinates
            - coord_destination: TUPLE of two values referring to the destination coordinates
            - typePreference: INTEGER Value to indicate the preference selected:
                                0 - Adjacency
                                1 - minimum Time
                                2 - minimum Distance
                                3 - minimum Transfers
                                4 - minimum Stops
            - city: CITYINFO with the information of the city (see CityInfo class definition)
            - flag_redundants: [0/1]. Flag to indicate if the algorithm has to remove the redundant paths (1) or not (0)

    OUTPUTS:
            - time: REAL total required time to make the route
            - distance: REAL total distance made in the route
            - transfers: INTEGER total transfers made in the route
            - stopStations: INTEGER total stops made in the route
            - num_expanded_nodes: INTEGER total expanded nodes to get the optimal path
            - depth: INTEGER depth of the solution
            - visitedNodes: LIST of INTEGERS, IDs of the stations corresponding to the visited nodes
            - idsOptimalPath: LIST of INTEGERS, IDs of the stations corresponding to the optimal path
            (from origin to destination)
            - min_distance_origin: REAL the distance of the origin_coordinates to the closest station
            - min_distance_destination: REAL the distance of the destination_coordinates to the closest station


            EXAMPLE:
            return optimalPath.time, optimalPath.walk, optimalPath.transfers,optimalPath.num_stopStation,
            len(expandedList), len(idsOptimalPath), visitedNodes, idsOptimalPath, min_distance_origin,
            min_distance_destination
    """
    # list with possible starting nodes
    stations_start = coord2station(coord_origin, stationList)
    # list with possible ending nodes
    stations_end = coord2station(coord_destination, stationList)
    costTable = setCostTable(typePreference, stationList, city)
    num_starts = len(stations_start)
    num_ends = len(stations_end)
    origins = []
    for station_id in stations_start:
        origins.append(Node(stationList[station_id], None))
    # if only one destination call directly A*, otherwise call multiple A*
    if num_ends == 1:
        solution = AStar(origins, Node(stationList[stations_end[0]], None), coord_origin,
                  coord_destination, stationList, costTable, typePreference, city, flag_redundants)
    else:
        # we call de AStar function for each origin, destination pair
        f = INFINITY
        for i in range(num_starts):
            partialSolution = None
            for j in range(num_ends):
                partialSolution = None
                partialSolution = AStar(origins, Node(stationList[stations_end[j]], None), coord_origin,
                                 coord_destination, stationList, costTable, typePreference, city, flag_redundants)
                if partialSolution[0][0][0].f < f:
                    f = partialSolution[0][0][0].f
                    solution = partialSolution

    return solution[0][0][0].time, solution[0][0][0].walk, solution[0][0][0].transfers, solution[0][0][0].num_stopStation,\
           len(solution[1]), len(solution[0][0]) - 1, solution[1], solution[2], solution[3], solution[4]


def AStar(origins, destination, p_origin, p_destination, stationList, costTable, typePreference, city, flag_redundants):
    """
    AStar : Does de A* Algorithm
    :param
            - origin:  Origin Node.
            - destination: Destination Node
            - p_origin: Origin node coordinates
            - p_destination: Destination node coordinates
            - stationList: List of the stations of the city
            - costTable: Adjacency table containing the costs between node i, j
            - typePreference: Type preference given by the user to choose the best path
            - city: The city we are calculating the paths in
            - flag_redundants: parameter indicating if it is desired to remove (1) or not (0) the redundant paths

    """
    listPaths = []
    visited_nodes = []
    tcp = {}
    for origin in origins:
        listPaths.append([origin])
    min_distance_origin = math.sqrt(math.pow(listPaths[0][0].station.x - p_origin[0], 2) + math.pow(listPaths[0][0].station.y - p_origin[1], 2))
    min_distance_destination = math.sqrt(math.pow(destination.station.x - p_destination[0], 2) + math.pow(destination.station.y - p_destination[1], 2))
    while len(listPaths) > 0 and listPaths[0][0].station.id != destination.station.id:
        C = listPaths[0][0]
        visited_nodes.append(C.station.id)
        E = Expand(C, stationList, typePreference, destination, costTable, city)
        E = RemoveCycles(E)
        if flag_redundants == 1:
            E = RemoveRedundantPaths(E, listPaths, tcp)

        processing_path = listPaths[0]
        listPaths = listPaths[1:]
        insertOrderedByF(E, processing_path, listPaths, typePreference)

    stations_ids = getStationsIds(listPaths[0])

    return [listPaths, visited_nodes, stations_ids, min_distance_origin, min_distance_destination]