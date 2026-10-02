function isArray(value: any): value is any[] {
  return Array.isArray(value);
}

function isPlainObject(value: any): boolean {
  return Object.prototype.toString.call(value) === '[object Object]';
}

function isObject(value: any): boolean {
  const type = typeof value;
  return type === 'function' || (type === 'object' && !!value);
}

function convertArray(value: any): any[] {
  if (isArray(value)) {
    return value;
  }
  if (isPlainObject(value)) {
    const keys = Object.keys(value);
    const res = new Array(keys.length);
    let i = keys.length;
    while (i--) {
      const key = keys[i];
      res[i] = { $key: key, $value: value[key] };
    }
    return res;
  }
  return value || [];
}

function multiIndex(obj: any, path: string[]): any {
  return path.length ? multiIndex(obj[path[0]], path.slice(1)) : obj;
}

function getPath(obj: any, path: string): any {
  return multiIndex(obj, path.split('.'));
}

function contains(value: any, search: string): boolean | undefined {
  let i: number;
  if (isPlainObject(value)) {
    const keys = Object.keys(value);
    i = keys.length;
    while (i--) {
      if (contains(value[keys[i]], search)) {
        return true;
      }
    }
  } else if (isArray(value)) {
    i = value.length;
    while (i--) {
      if (contains(value[i], search)) {
        return true;
      }
    }
  } else if (value != null) {
    return value.toString().toLowerCase().indexOf(search) > -1;
  }
  return undefined;
}

function flatten(values: any[]): any[] {
  const out: any[] = [];
  for (let i = 0; i < values.length; i++) {
    const value = values[i];
    if (isArray(value)) {
      for (let j = 0; j < value.length; j++) {
        out.push(value[j]);
      }
    } else {
      out.push(value);
    }
  }
  return out;
}

export function filterBy(collection: any, search: any, ...keys: string[]): any {
  const source = convertArray(collection);
  if (search == null) {
    return source;
  }
  if (typeof search === 'function') {
    return source.filter(search);
  }
  const term = ('' + search).toLowerCase();
  const flatKeys: string[] = flatten(keys);
  const res: any[] = [];
  for (let i = 0, l = source.length; i < l; i++) {
    const item = source[i];
    const value = (item && item.$value) || item;
    if (flatKeys.length) {
      let j = flatKeys.length;
      while (j--) {
        const key = flatKeys[j];
        if ((key === '$key' && contains(item.$key, term)) || contains(getPath(value, key), term)) {
          res.push(item);
          break;
        }
      }
    } else if (contains(item, term)) {
      res.push(item);
    }
  }
  return res;
}

export function orderBy(collection: any, ...args: any[]): any[] {
  const source = convertArray(collection);
  let order = args[args.length - 1];
  let keys = args;
  if (typeof order === 'number') {
    order = order < 0 ? -1 : 1;
    keys = args.length > 1 ? args.slice(0, -1) : [];
  } else {
    order = 1;
  }

  const firstArg = keys[0];
  if (!firstArg) {
    return source;
  }

  const sortKeys: string[] = typeof firstArg === 'function' ? [] : flatten(keys);
  const baseCompare = (a: any, b: any, sortKeyIndex: number): number => {
    const sortKey = sortKeys[sortKeyIndex];
    if (sortKey) {
      if (sortKey !== '$key') {
        if (isObject(a) && '$value' in a) {
          a = a.$value;
        }
        if (isObject(b) && '$value' in b) {
          b = b.$value;
        }
      }
      a = isObject(a) ? getPath(a, sortKey) : a;
      b = isObject(b) ? getPath(b, sortKey) : b;
      a = typeof a === 'string' ? a.toLowerCase() : a;
      b = typeof b === 'string' ? b.toLowerCase() : b;
    }
    return a === b ? 0 : a > b ? order : -order;
  };

  const compare = (a: any, b: any, i = 0): number =>
    i >= sortKeys.length - 1 ? baseCompare(a, b, i) : baseCompare(a, b, i) || compare(a, b, i + 1);

  const comparator = typeof firstArg === 'function' ? (a: any, b: any) => firstArg(a, b) * order : compare;

  return source.slice().sort(comparator);
}
