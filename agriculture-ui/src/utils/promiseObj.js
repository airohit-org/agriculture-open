const promiseObj = (obj = {}) => (
    new Promise((resolve, reject) => {
      const result = {};
      const keys = Object.keys(obj);
      keys.forEach(key => {
        Promise.resolve(obj[key]).then(value=>{
            result[key] = {
                value
            }
        }).catch(error => {
            result[key] = {
                error
            }
        }).finally(() => {
            if(Object.keys(result).length === keys.length){
                resolve(result)
            }
        })
      })
    })
);

export default promiseObj;