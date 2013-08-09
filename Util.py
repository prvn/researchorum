
class Util:

    @staticmethod
    def appendParam(url, key, value, first):
        if first:
            return url[:-1] + "?" + key + "=" + value
        else:
            return url + "&" + key + "=" + value