# -*- coding: utf-8 -*-
"""

@author: ramon, manuel, ibai
"""
import re
import numpy as np
import ColorNaming as cn
from skimage import color
import KMeans as km
from skimage.transform import rescale
from sklearn.preprocessing import normalize


def loadGT(filename):    # NO TOCAR tota la funcio
    """
    loadGT : Load ground truth parameters from file
    :param
        - filename: STRING path of the file that contains groundtruth info
    :return
        - GT: LIST list of tuples (Name, [list-of-labels])
    """
    GT = []
    fd = open(filename, 'r')
    for line in fd:
        splitLine = line.split(' ')[:-1]
        labels = [''.join(sorted(filter(None,re.split('([A-Z][^A-Z]*)',l)))) for l in splitLine[1:]]
        GT.append( (splitLine[0], labels) )
        
    return GT


def evaluate(description, GT, options): # NO TOCAR
    """
    evaluate : calculates the average porcentage of success from all the images given
    :param
        - description: LIST of LISTs labels founded by algorithm
        - GT: LIST of LISTs labels of ground truth
        - options: DICT app options
    :return
        - puntuation: FLOAT encert porcentage
    """
    scores = 0
    for pos, d in enumerate(description):
        scores += similarityMetric(d, GT[pos][1], options)

    return scores/len(description), scores


def similarityMetric(Est, GT, options):# NO TOCAR
    """
    similarityMetric : number that determines succes porcentage between our labels and ground truth
    :param
        - Est: LIST calculated labels
        - GT: LIST ground truth labels
        - options: DICT program options
    :return
        - div: FLOAT encert porcentage
    """
    score = 0.0
    div = 0.0
    if options['metric'] == 'basic':
        for good_label in GT:
            for label in Est:

                if label == good_label:
                    score += 1.0
                    break
                else:
                    chunks = re.findall('[A-Z][^A-Z]*', label)
                    if len(chunks) > 1:
                        new_label = chunks[1] + chunks[0]
                        if new_label == good_label:
                            score += 1.0
                            break
        num_elements = len(GT) * 1.0
        div = score/num_elements
    elif options['metric'] == 'other1':
        for good_label in GT:
            found = False
            for label in Est:
                if label == good_label:
                    score += 1.0
                    found = True
                    break
                else:
                    chunks = re.findall('[A-Z][^A-Z]*', label)
                    if len(chunks) > 1:
                        new_label = chunks[1] + chunks[0]
                        if new_label == good_label:
                            score += 1.0
                            found = True
                            break
            if not found:
                score -= 0.25
        num_elements = len(GT) * 1.0
        div = score/num_elements

    return div


def getLabels(kmeans, options):    # NO TOCAR
    colors = []
    unique = []
    options['labelsType'] = options['K']
    options['labelThrs'] = 1 - options['single_thr']
    options['colorSpace'] = 'Potentials'

    for pos, centroid in enumerate(kmeans.centroids):
        go_next = False
        # Buscamos el numero mas grande de la lista
        maximum_value = max(centroid)
        # Recogemos una lista con las posiciones de ese o esos numeros mas grandes
        centroid_pos = maxs_value_list(centroid)
        maximum_len = len(centroid_pos)
        # Si hay SOLO UNO mayor o igual al THR metemos en la lista de colores el color y
        # en la lista de indices de centroide que centroide es
        if maximum_value >= options['single_thr'] and maximum_len == 1:
            try:
                idx = colors.index(cn.colors[centroid_pos[0]])

                unique[idx].append(pos)
            except ValueError:
                unique.append([pos])
            colors.append(cn.colors[centroid_pos[0]])
            go_next = True
        # si no lo encontramos buscamos los 2 colores mas abundantes en el centroide
        if not go_next:
            tmp_centroid = centroid
            maxs1 = maxs_value_list(tmp_centroid)
            max1_pos = maxs1[0]
            str1 = cn.colors[max1_pos]
            # El ya usado lo cambiamos por 0 para no volverlo a coger
            tmp_centroid[max1_pos] = 0.0
            maxs2 = maxs_value_list(tmp_centroid)
            max2_pos = maxs2[0]
            str2 = cn.colors[max2_pos]
            if str1 > str2:
                color = str2+str1
            else:
                color = str1+str2
            try:
                idx = colors.index(color)
                unique[idx].append(pos)
            except ValueError:
                unique.append([pos])
            colors.append(color)

    # colors, unique = cn.ClusterColorNaming(kmeans.centroids, options)
    for i, color in enumerate(colors):
        for j, other_color in enumerate(colors):
            if i != j and color == other_color:
                colors.remove(other_color)

    return colors, unique


def maxs_value_list(L):
    """ Devuelve una lista con los maximos valores de la lista"""
    m = max(L)
    return [i for i, j in enumerate(L) if j == m]

def bestKElbow(X, options):
    K = 2
    finish = 10
    kmeans_list = []
    fits = []
    porcentage = []
    tolerance = 10 # 10% de tolerancia
    finished = False
    best_k = 0

    # kmeans con k=2
    kmeans = km.KMeans(X, K, options)
    kmeans_list.append(kmeans)
    fits.append(kmeans.fitting())

    K += 1

    # kmeans con k=3
    kmeans = km.KMeans(X, K, options)
    kmeans_list.append(kmeans)
    fits.append(kmeans.fitting())
    K += 1

    # Guardamos la resta y le asignamos un porcentage de 100%
    porcentage.append((fits[0] - fits[1], 100))

    while K <= finish and not finished:
        best_k = 0
        kmeans = km.KMeans(X, K, options)
        kmeans_list.append(kmeans)
        fits.append(kmeans.fitting())
        K += 1

        # restamos el penultimo con el antepenultimo (ultimos 2 kmeans calculados)
        resta = fits[-2] - fits[-1]
        # calculamos el porcentage respecto a la primera resta
        first_resta = porcentage[0][0]
        if first_resta == 0:
            first_resta = 0.000000000001
        porcentage.append((resta, resta * 100 / first_resta))

        # si las 2 ultimos porcentajes estan por debajo de la tolerancia cogemos la k anterior a estos 2
        if (porcentage[-2][1] < tolerance and porcentage[-1][1] < tolerance):
            # print "por telerancia ssale"
            finished = True
            # le kito por el valor k empieza la k y las 2 k me paso
            best_k = K - 2 - 2 - 1

    # Si no se ha cumplido el factor de tolerancia buscamos el % mas grande de caida y elegimos su segunda K
    if best_k == 0:
        max_porcentage = porcentage[1][1]
        porcentage_pos = 1
        for pos, x in enumerate(porcentage[2:]):
            if x[1] > max_porcentage:
                max_porcentage = x[1]
                porcentage_pos = pos

        best_k = porcentage_pos

    return kmeans_list[best_k]


def processImage(im, options): # NO TOCAR
    """
    processImage : Converts image to configured color space
    :param
        - im: Image to process
        - options: DICT program options
    :return
        - colors : LIST of our label
        - wich : ???
        - kmeans : KMEANS class
    """
    if options['colorspace'] == 'ColorNaming':  # NO TOCAR
        im = cn.ImColorNamingTSELabDescriptor(im)
    elif options['colorspace'] == 'RGB':        # NO TOCAR
        im = im
    elif options['colorspace'] == 'Lab':        # NO TOCAR
        im = color.rgb2lab(im)

    X = np.reshape(im, (-1, im.shape[2]))

    if options['K'] < 2:
        kmeans = bestKElbow(X, options)
    else:
        kmeans = km.KMeans(X, options['K'], options)  # NO TOCAR

    if options['colorspace'] == 'RGB':
        kmeans.centroids = cn.ImColorNamingTSELabDescriptor(kmeans.centroids)
    elif options['colorspace'] == 'Lab':
        kmeans.centroids = np.reshape(kmeans.centroids, (-1, 1, kmeans.centroids.shape[1]))
        kmeans.centroids = color.lab2rgb(kmeans.centroids) * 255
        kmeans.centroids = np.reshape(kmeans.centroids, (-1, kmeans.centroids.shape[2]))
        kmeans.centroids = cn.ImColorNamingTSELabDescriptor(kmeans.centroids)


    colors, which = getLabels(kmeans, options)   # NO TOCAR
    return colors, which, kmeans                 # NO TOCAR