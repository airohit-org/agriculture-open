import JSEncrypt from 'jsencrypt/bin/jsencrypt.min'

// 密钥对生成 http://web.chacuo.net/netrsakeypair

const publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCujrpp9j3j+cjhP9+OYeaoh87hxh0mmM6Qr5foAtcBm1ayw0dpGh/BI1Rw2zUbSXZvx5svtlKDnSjXq+uY9nTJiXMsrNFSTrhwKQUMn4xv0T4gKNpN4Fx1gARAAHyd7po7hvs8Ia202gH2tGqzkUnhEZ4nZ4BP1+EXsyR0uc7fzwIDAQAB'

const privateKey = 'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK6Oumn2PeP5yOE/345h5qiHzuHGHSaYzpCvl+gC1wGbVrLDR2kaH8EjVHDbNRtJdm/Hmy+2UoOdKNer65j2dMmJcyys0VJOuHApBQyfjG/RPiAo2k3gXHWABEAAfJ3umjuG+zwhrbTaAfa0arORSeERnidngE/X4RezJHS5zt/PAgMBAAECgYAF2FSoqKyw3hPaawue1mtlWErWp44hVTuRf7e79qBhduSjzsMs40mN65Nvxf9UvxR+sLXjFp1Tt+b+tcyJTjNh3/+o0iT6VzIqgCLAuWbPpYRIcvRHE0LwuDraK9A/bGhehmGbTNdrmcUaj3auwza0cmMp3zGZi/Ngx33E/cxoPQJBAOyWSY2y/o79r1TKoMcH1MYdKKIaAJguj8cfIqLxVPfSudRBM0gFYa/kXVScQKn3P5lrL9+x/KqOqO4hKkRn1LsCQQC84XUyY9r9jlfN10fW8GO165niAerDEuTT1X6UCBQZ28ESMZXGnfMEtCmcUnnqPjKFBK7QBNVM0+SqPBqfPDn9AkEAwUZJUnQu11vRd2CNlesE3NAHWN4LLEeld5Ms9ZZXxpkk7IxTO3wCAPJ7eFqpzPBKyaeyFXnPIvqmcNsxL27vbwJAewmWsVtwtMi7k5/P8Uioe0Hc/OdoyOgO3ZruGHnmti17aGGagkYznvXJQUZMmnUnrZSuV10+oQxKoUYDxYSS9QJBAM4OTJhZ1yVPVy3Ez8+14qyzOCQrT+pYSPdU13T8QizXjspQdU4+TJZ8/2BJeshSfEdhvhooXe5Zn5X6Peb+sB8='


// 加密
export function encrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPublicKey(publicKey) // 设置公钥
  return encryptor.encrypt(txt) // 对数据进行加密
}

// 解密
export function decrypt(txt) {
  const encryptor = new JSEncrypt()
  encryptor.setPrivateKey(privateKey) // 设置私钥
  return encryptor.decrypt(txt) // 对数据进行解密
}

