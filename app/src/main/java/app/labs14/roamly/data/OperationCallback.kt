package app.labs14.roamly

interface OperationCallback {
    fun onSuccess(obj:Any?)
    fun onError(obj:Any?)
}