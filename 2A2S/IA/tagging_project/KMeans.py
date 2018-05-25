# -*- coding: utf-8 -*-
"""

@author: ramon, manuel, ibai
"""
import numpy as np
import matplotlib.pyplot as plt
import mpl_toolkits.mplot3d.axes3d as axes3d
from sklearn.decomposition import PCA
import random
reload(random)


def distance(x, y):  # NO TOCAR
    """
    distance : Calculates the Euclidiane distance between 2 points in any dimension
    :param
        - x: LIST point1
        - y: LIST point2
    :return
        - dis: FLOAT distance
    """
    dis = np.sqrt(distance_squared(x, y))
    return dis
    
    
def distance_squared(x, y):  # NO TOCAR
    """
    distance_no_sqrt : Calculates distance powered to two between 2 points in any dimension
    :param
        - x: LIST point1
        - y: LIST point2
    :return
        - distance: FLOAT distance
    """
    dis = 0.0
    for num in range(len(x)):
        dis += np.power(x[num] - y[num], 2)
    return dis
    
    
def round_number_2(num):
    """
    round_number_2 :Rounds a number to two decimals
    :param
        - num: FLOAT number to round
    :return 
        - num: FLOAT number rounded
    """
    num = float("{0:.2f}".format(num))
    return num
    
       
class KMeans:    # NO TOCAR
    """
    KMeans : K-means algorithm class
    """
    def __init__(self, X, K, options=None):     # NO TOCAR
        """
        __init__ : Class constructor
        :param
            - X: LIST image point
            - K: INT number of clusters
            - options: DICT parameters for configure Kmeans algorithm
        """
        if options is not None:                     # NO TOCAR
            options = {}                        # NO TOCAR
        if 'km_init' not in options:            # NO TOCAR
            options['km_init'] = 'first'        # NO TOCAR
        if 'verbose' not in options:            # NO TOCAR
            options['verbose'] = False          # NO TOCAR
        self.options = options                  # NO TOCAR        
        self.K = K                              # NO TOCAR
        self.X = X
        self._init_centroids()                  # NO TOCAR
        self.clusters = np.zeros(len(self.X), dtype=np.int)
        self._init_fisher_list()
        self.intra = []
        self.inter = []
        self._iterate()                         # NO TOCAR

    def _init_fisher_list(self):
        """
        _init_fisher_list : Initialize fisher list
        """
        self.fisher_list = []
        for i in range(self.K):
            self.fisher_list.append([])
            
    def _init_centroids(self):  # NO TOCAR
        """
        _init_centroids : Initialize clusters by a given parameter ['km_init']
        """
        self.centroids = np.zeros((self.K, len(self.X[0])))
        self.old_centroids = np.zeros((self.K, len(self.X[0])))
        current_option = self.options['km_init']
        
        if current_option is 'first':
            for num in range(self.K):
                self.centroids[num] = self.X[num]
        elif current_option is 'random':
            for num in range(self.K):
                self.centroids[num] = self.X[random.randint(0, len(self.X) - 1)]
        elif current_option is 'distributed':
            for num in range(self.K):
                self.centroids[num] = self.X[num * (len(self.X) / self.K)]
        elif current_option is 'landscape' and self.K == 5:
            square_root = int(np.sqrt(len(self.X)))
            self.centroids[0] = self.X[0]
            self.centroids[1] = self.X[square_root]
            self.centroids[2] = self.X[len(self.X) / 2]
            self.centroids[3] = self.X[len(self.X) - square_root]
            self.centroids[4] = self.X[-1]
        elif current_option is 'last':
            for num in range(self.K):
                self.centroids[num] = self.X[(-num - 1)]
        # medium
        else:
            for num in range(self.K):
                self.centroids[num] = self.X[(len(self.X) / 2) - (self.K / 2) + num]

            
    def _cluster_points(self):  # NO TOCAR  
        """
        _cluster_points : Assign each image point to its cluster
        """      
        self._init_fisher_list()
        for pos, pixel in enumerate(self.X):
            i = 0
            best_i = 0
            best_distance = float('inf')
            for centroid in self.centroids:
                current_distance = distance(pixel, centroid)
                if current_distance < best_distance:
                    best_distance = current_distance
                    best_i = i
                i += 1
            self.clusters[pos] = best_i
            self.fisher_list[best_i].append(pixel)
        
    def _get_centroids(self):  # NO TOCAR
        """
        _get_centroids : Recalculates centroids
        """
        self.old_centroids = np.copy(self.centroids)
        pixel_arr = [[0.0 for x in range(len(self.X[0]))] for y in range(self.K)] 
        pixel_quant = [0] * self.K

        for pos, cluster in enumerate(self.clusters):
            for dimension in range(len(self.X[pos])):          
                pixel_arr[cluster][dimension] += self.X[pos][dimension]
            pixel_quant[cluster] += 1
        
        for pos in range(self.K):
            if pixel_quant[pos] > 0:
                for dimension in range(len(pixel_arr[pos])):
                    self.centroids[pos][dimension] = pixel_arr[pos][dimension] / pixel_quant[pos]
            
    def _converges(self):  # NO TOCAR
        """
        _converges : Compare previous centroids with actual centroids and checks if it has converged
                      They are converged if the difference between the centroids is equal to a certain tolerance
                      
        """
        converge = False
        i = 0
        close = True
        while close and i < self.K:
            close = np.allclose(self.centroids[i], self.old_centroids[i], rtol=0.0)
            i += 1
        if i == self.K:
            converge = True
        return converge
        
    def _iterate(self):                     # NO TOCAR
        """
        _iterate : Iterates until centroids converge
        """          
        self.num_iter = 1                   # NO TOCAR
        self._cluster_points()              # NO TOCAR
        self._get_centroids()               # NO TOCAR
        if self.options['verbose']:         # NO TOCAR
            self.plot()                     # NO TOCAR

        while not self._converges() :       # NO TOCAR
            self._cluster_points()          # NO TOCAR
            self._get_centroids()           # NO TOCAR
            self.num_iter += 1              # NO TOCAR
            if self.options['verbose']:     # NO TOCAR
                self.plot(False)            # NO TOCAR
        
    def fitting(self):  # NO TOCAR
        """
        fitting : Measures the internal distance of a cluster and the distance between clusters.
        :return
            fisher: FLOAT mesure 
        """
        #intra
        # Inicializamos una lista con tantas posiciones como classes tiene este K-MEANS
        self.intra = [0.0] * self.K
        # Para cada uno de los clusters
        for pos, cluster in enumerate(self.fisher_list):
            # Para cada punto
            for point in cluster:
                # Miramos la distancia respecto a otro punto
                for next_point in cluster:
                    # Sumamos todas las distancias de los puntos de un cluster
                    self.intra[pos] += distance_squared(point, next_point)

        # Una vez tenemos todas las sumas lo multiplicamos por el 1 / (numero de puntos * num puntos - 1) cada una de ellas
        for i in range(len(self.intra)):
            n_points = len(self.fisher_list[i])
            val = (1.0 / (n_points * (n_points - 1))) * self.intra[i]
            self.intra[i] = val

        #inter
        # Inicializamos una lista para guardar las intras de las parejas de clases
        self.inter = [0.0] * ((self.K * (self.K - 1)) / 2)
        # var para saber en que pos va del inter
        pos = 0
        # Recorremos todos los clusters
        for n_cluster, cluster in enumerate(self.fisher_list[0:len(self.fisher_list) - 1]):
            # Queremos comparar el actual con los siguientes asi que vamos cogiendo a partir del cluster actual
            # hasta el final de la lista
            # ejemplo si 3 clusters (A,B,C) A-->[A,B],[A,C] | B-->[B,C] | C -->nada
            # distancia de los puntos de las clases [] multiplicado por 1 / (len class1 * len class 2)
            len_c1 = len(cluster)
            for cluster2 in self.fisher_list[n_cluster+1:self.K]:
                # cada punto de el primer cluster miramos su distancia con los puntos del segundo cluster
                len_c2 = len(cluster2)
                for point_c1 in cluster:
                    for point_c2 in cluster2:
                        # guardamos en la posicion que toca la suma
                        self.inter[pos] += distance_squared(point_c1, point_c2)
                val = (1.0 / (len_c1 * len_c2)) * self.inter[pos]
                self.inter[pos] = val
                pos += 1

        #suma de todas las intras / suma de todas las inter para tener fisher
        fisher = sum(self.intra) / sum(self.inter)
        
        return fisher
        
    def plot(self, first_time=True): # NO TOCAR res de la funcio
        """
        plot : Plots kmeans image points and centroids
        :param
            - X: LIST image point
            - K: INT number of clusters
            - options: DICT parameters for configure Kmeans algorithm
        """
        # markersshape = 'ov^<>1234sp*hH+xDd'
        markerscolor = 'bgrcmybgrcmybgrcmyk'

        if first_time:
            plt.gcf().add_subplot(111, projection='3d')
            plt.ion()
            plt.show()
            if self.X.shape[1] > 3:
                self.pca = PCA(n_components=3)
                self.pca.fit(self.X)

        if self.X.shape[1]>3:
            Xt = self.pca.transform(self.X)
            Ct = self.pca.transform(self.centroids)
        else:
            Xt = self.X
            Ct = self.centroids

        for k in range(self.K):
            plt.gca().plot(Xt[self.clusters == k, 0], Xt[self.clusters == k, 1], Xt[self.clusters == k, 2], '.' + markerscolor[k])
            plt.gca().plot(Ct[k, 0:1], Ct[k, 1:2], Ct[k, 2:3], 'o'+'k', markersize=12)
        plt.draw()
        plt.pause(0.01)
