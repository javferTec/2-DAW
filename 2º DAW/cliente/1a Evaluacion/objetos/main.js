function isEquals(obj1, obj2) {
    const keys1 = Object.keys(obj1);
    const keys2 = Object.keys(obj2);

    if (keys1.length !== keys2.length) {
        return false;
    }

    for (let key of keys1) {
        if (obj1[key] !== obj2[key]) {
            return false;
        }
    }

    return true;
}

function compararObjetos() {
    const obj1 = { a: 1, b: 2 };
    const obj2 = { b: 2, a: 1 };

    if (isEquals(obj1, obj2)) {
        console.log("Los objetos son iguales");
    } else {
        console.log("Los objetos son diferentes");
    }
}