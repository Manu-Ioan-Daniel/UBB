#merge sort
def my_merge_sort(list,key=None,reverse=False):
    if key is None:
        key=lambda x:x
    if len(list)<=1:
        return list
    mid=len(list)//2
    left=my_merge_sort(list[:mid],key,reverse)
    right=my_merge_sort(list[mid:],key,reverse)
    return merge(left,right,key,reverse)
def merge(left,right,key,reverse):
    result=[]
    i=j=0
    while i<len(left) and j<len(right):
        if (key(left[i])<=key(right[j])) != reverse:
            result.append(left[i])
            i+=1
        else:
            result.append(right[j])
            j+=1
    result.extend(left[i:])
    result.extend(right[j:])
    return result
def sortare(list,key=None,reverse=False):
    return my_merge_sort(list,key,reverse)


