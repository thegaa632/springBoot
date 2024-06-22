async function getList({bno, page, size, goList}) {
    const result = await axios.get(`/replies/list/${bno}`, {params : {page, size}})
    // console.log(`bno : ${bno}`);
    // console.log("result : " + JSON.stringify(result.data));

    return result.data;
}